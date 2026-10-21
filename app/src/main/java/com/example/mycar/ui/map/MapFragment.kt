package com.example.mycar.ui.map

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Network
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import android.os.CancellationSignal
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapFragment : Fragment() {
    private var mapView: MapView? = null
    private var started = false
    private var state by mutableStateOf(MapUiState())
    private var camera = CameraPosition(Point(59.934280, 30.335099), 11f, 0f, 0f)
    private val markers = linkedMapOf<MapPlace, PlacemarkMapObject>()
    // MapKit keeps weak references to listeners; retain them for this view's lifetime.
    private val markerListeners = mutableListOf<MapObjectTapListener>()
    private var userMarker: PlacemarkMapObject? = null
    private var userPoint: Point? = null
    private var filterJob: Job? = null
    private var locationTimeout: Job? = null
    private var locationCancellation: CancellationSignal? = null
    private var connectivity: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    private val permissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
        if (granted.values.any { it }) {
            if (started && mapView != null) locate()
        } else {
            state = state.copy(message = "Без разрешения карта работает, но вашу позицию определить нельзя. Доступ можно разрешить в настройках приложения.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            mapPlaces.firstOrNull { it.companyName == arguments?.getString("selected") }?.let { selected ->
                state = state.copy(selected = selected)
                camera = CameraPosition(Point(selected.latitude, selected.longitude), 13f, 0f, 0f)
            }
        }
        savedInstanceState?.let { saved ->
            val query = saved.getString("query").orEmpty()
            val types = saved.getStringArrayList("types").orEmpty().mapNotNull { name -> PointType.entries.firstOrNull { it.name == name } }.toSet()
            val matches = filterMapPlaces(mapPlaces, query, types)
            state = state.copy(query = query, types = types, matches = matches,
                selected = matches.firstOrNull { it.companyName == saved.getString("selected") })
            if (saved.containsKey("lat")) camera = CameraPosition(Point(saved.getDouble("lat"), saved.getDouble("lon")),
                saved.getFloat("zoom", 11f), saved.getFloat("azimuth"), saved.getFloat("tilt"))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val matches = filterMapPlaces(mapPlaces, state.query, state.types)
        state = state.copy(matches = matches, selected = state.selected?.takeIf { it in matches })
        val nativeMap = MapView(requireContext())
        mapView = nativeMap
        nativeMap.mapWindow.map.apply {
            isNightModeEnabled = true
            move(camera)
        }
        // Allocate the four marker images once; filtering only changes visibility.
        val images = PointType.entries.associateWith { ImageProvider.fromBitmap(markerBitmap(it)) }
        mapPlaces.forEach { place ->
            val marker = nativeMap.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(place.latitude, place.longitude)
                setIcon(images.getValue(place.type))
                isVisible = place in state.matches
            }
            val listener = MapObjectTapListener { _, _ -> selectPlace(place); true }
            marker.addTapListener(listener)
            markers[place] = marker
            markerListeners += listener
        }
        userPoint?.let { updateUserMarker(it) }
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ServiceMapScreen(state,
                    onQuery = { query ->
                        state = state.copy(query = query)
                        filterJob?.cancel()
                        filterJob = viewLifecycleOwner.lifecycleScope.launch { delay(180); applyFilters() }
                    },
                    onTypes = { types -> state = state.copy(types = types); filterJob?.cancel(); applyFilters() },
                    onSelect = ::selectPlace, onLocate = ::requestLocation,
                    onZoom = { delta ->
                        mapView?.mapWindow?.map?.let { map ->
                            val position = map.cameraPosition
                            map.move(CameraPosition(position.target, (position.zoom + delta).coerceIn(3f, 20f), position.azimuth, position.tilt))
                        }
                    },
                    onRoute = ::openRoute,
                    onDismissMessage = { state = state.copy(message = null) }
                ) { AndroidView(factory = { nativeMap }, modifier = Modifier.fillMaxSize()) }
            }
        }
    }

    private fun applyFilters() {
        val matches = filterMapPlaces(mapPlaces, state.query, state.types)
        val visible = matches.toSet()
        markers.forEach { (place, marker) -> marker.isVisible = place in visible }
        state = state.copy(matches = matches, selected = state.selected?.takeIf { it in visible })
    }

    private fun selectPlace(place: MapPlace?) {
        state = state.copy(selected = place)
        place?.let {
            mapView?.mapWindow?.map?.let { map ->
                map.move(CameraPosition(Point(it.latitude, it.longitude), map.cameraPosition.zoom.coerceAtLeast(13f), 0f, 0f))
            }
        }
    }

    private fun hasPermission(permission: String): Boolean = context?.let {
        ContextCompat.checkSelfPermission(it, permission) == PackageManager.PERMISSION_GRANTED
    } == true

    private fun requestLocation() {
        if (!started) return
        if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) || hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) locate()
        else permissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun locate() {
        val screenContext = context ?: return
        val activeMap = mapView ?: return
        if (!started) return
        cancelLocation()
        val manager = screenContext.getSystemService(Context.LOCATION_SERVICE) as? LocationManager ?: return
        val fine = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        try {
            val enabled = manager.getProviders(true)
            val provider = when {
                LocationManager.NETWORK_PROVIDER in enabled -> LocationManager.NETWORK_PROVIDER
                fine && LocationManager.GPS_PROVIDER in enabled -> LocationManager.GPS_PROVIDER
                else -> null
            }
            if (provider == null) {
                state = state.copy(message = "Включите геолокацию устройства. Для приблизительной позиции также нужен доступный сетевой провайдер.")
                return
            }
            val signal = CancellationSignal()
            locationCancellation = signal
            state = state.copy(locating = true, message = null)
            val owner = viewLifecycleOwner
            locationTimeout = owner.lifecycleScope.launch {
                delay(15000)
                if (locationCancellation === signal) {
                    signal.cancel()
                    locationCancellation = null
                    state = state.copy(locating = false, message = "Не удалось получить позицию за 15 секунд. Попробуйте ещё раз на открытом месте.")
                }
            }
            LocationManagerCompat.getCurrentLocation(manager, provider, signal, ContextCompat.getMainExecutor(screenContext)) { location ->
                if (locationCancellation !== signal || mapView !== activeMap || !started ||
                    !owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) return@getCurrentLocation
                locationTimeout?.cancel()
                locationTimeout = null
                locationCancellation = null
                state = state.copy(locating = false)
                if (location == null) state = state.copy(message = "Позиция пока недоступна. Проверьте геолокацию и попробуйте ещё раз.")
                else acceptLocation(location)
            }
        } catch (_: SecurityException) {
            cancelLocation()
            state = state.copy(message = "Доступ к местоположению недоступен. Проверьте разрешения приложения.")
        } catch (_: IllegalArgumentException) {
            cancelLocation()
            state = state.copy(message = "На устройстве нет доступного провайдера геолокации.")
        }
    }

    private fun acceptLocation(location: Location) {
        val point = Point(location.latitude, location.longitude)
        userPoint = point
        updateUserMarker(point)
        mapView?.mapWindow?.map?.move(CameraPosition(point, 15f, 0f, 0f))
        if (location.hasAccuracy() && location.accuracy > 1000f)
            state = state.copy(message = "Показано приблизительное местоположение. Точность зависит от разрешений и сигнала.")
    }

    private fun updateUserMarker(point: Point) {
        val map = mapView?.mapWindow?.map ?: return
        val marker = userMarker ?: map.mapObjects.addPlacemark().also {
            it.setIcon(ImageProvider.fromBitmap(markerBitmap(null)))
            userMarker = it
        }
        marker.geometry = point
    }

    private fun cancelLocation() {
        locationTimeout?.cancel()
        locationTimeout = null
        locationCancellation?.cancel()
        locationCancellation = null
        state = state.copy(locating = false)
    }

    private fun openRoute(place: MapPlace) {
        val destination = "${place.latitude},${place.longitude}"
        val route = userPoint?.let { "${it.latitude},${it.longitude}~$destination" } ?: "~$destination"
        val web = Uri.Builder().scheme("https").authority("yandex.ru").path("maps/")
            .appendQueryParameter("rtext", route).appendQueryParameter("rtt", "auto").build()
        val navi = Uri.Builder().scheme("yandexnavi").authority("build_route_on_map")
            .appendQueryParameter("lat_to", place.latitude.toString()).appendQueryParameter("lon_to", place.longitude.toString())
            .apply { userPoint?.let { appendQueryParameter("lat_from", it.latitude.toString()); appendQueryParameter("lon_from", it.longitude.toString()) } }.build()
        val intents = listOf(Intent(Intent.ACTION_VIEW, web).setPackage("ru.yandex.yandexmaps"),
            Intent(Intent.ACTION_VIEW, navi).setPackage("ru.yandex.yandexnavi"), Intent(Intent.ACTION_VIEW, web))
        for (intent in intents) {
            try { startActivity(intent); return }
            catch (_: ActivityNotFoundException) { /* Try the next installed handler. */ }
            catch (_: SecurityException) { /* The handler may be unavailable to this app. */ }
        }
        state = state.copy(message = "Не найдено приложение для маршрута. Установите карты или браузер.")
    }

    override fun onStart() {
        super.onStart()
        mapView?.let {
            MapKitFactory.getInstance().onStart()
            it.onStart()
            started = true
            applyFilters()
        }
        observeConnectivity()
    }

    private fun observeConnectivity() {
        val screenContext = context ?: return
        val manager = screenContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return
        connectivity = manager
        val executor = ContextCompat.getMainExecutor(screenContext)
        fun refresh() = executor.execute {
            if (started && connectivity === manager) {
                val caps = manager.getNetworkCapabilities(manager.activeNetwork)
                state = state.copy(online = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true)
            }
        }
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { refresh() }
            override fun onLost(network: Network) { refresh() }
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) { refresh() }
        }
        try { manager.registerDefaultNetworkCallback(callback); networkCallback = callback; refresh() }
        catch (_: SecurityException) { state = state.copy(online = false) }
    }

    override fun onStop() {
        cancelLocation()
        filterJob?.cancel()
        filterJob = null
        started = false
        networkCallback?.let { callback ->
            try { connectivity?.unregisterNetworkCallback(callback) }
            catch (_: IllegalArgumentException) { /* Already unregistered by the platform. */ }
        }
        networkCallback = null
        connectivity = null
        mapView?.let { camera = it.mapWindow.map.cameraPosition; it.onStop(); MapKitFactory.getInstance().onStop() }
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mapView?.let { camera = it.mapWindow.map.cameraPosition }
        outState.putString("query", state.query)
        outState.putStringArrayList("types", ArrayList(state.types.map { it.name }))
        outState.putString("selected", state.selected?.companyName)
        outState.putDouble("lat", camera.target.latitude); outState.putDouble("lon", camera.target.longitude)
        outState.putFloat("zoom", camera.zoom); outState.putFloat("azimuth", camera.azimuth); outState.putFloat("tilt", camera.tilt)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        cancelLocation()
        filterJob?.cancel()
        filterJob = null
        markers.clear()
        markerListeners.clear()
        userMarker = null
        mapView = null
        super.onDestroyView()
    }

    private fun markerBitmap(type: PointType?): Bitmap {
        val bitmap = Bitmap.createBitmap(72, 72, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = android.graphics.Color.argb(55, 0, 0, 0)
        canvas.drawCircle(36f, 39f, 31f, paint)
        paint.color = android.graphics.Color.WHITE
        canvas.drawCircle(36f, 35f, 28f, paint)
        paint.color = when (type) {
            PointType.STO -> android.graphics.Color.rgb(255, 166, 102)
            PointType.TIRE -> android.graphics.Color.rgb(155, 213, 192)
            PointType.BODYWORK -> android.graphics.Color.rgb(183, 198, 255)
            PointType.POUND -> android.graphics.Color.rgb(255, 216, 141)
            null -> android.graphics.Color.rgb(61, 156, 245)
        }
        canvas.drawCircle(36f, 35f, 24f, paint)
        paint.color = if (type == null) android.graphics.Color.WHITE else android.graphics.Color.rgb(27, 30, 34)
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 26f
        paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
        val letter = when (type) { PointType.STO -> "С"; PointType.TIRE -> "Ш"; PointType.BODYWORK -> "К"; PointType.POUND -> "П"; null -> "•" }
        canvas.drawText(letter, 36f, 35f - (paint.ascent() + paint.descent()) / 2, paint)
        return bitmap
    }
}
