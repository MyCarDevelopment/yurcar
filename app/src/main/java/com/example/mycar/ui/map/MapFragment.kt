package com.example.mycar.ui.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mycar.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var infoContainer: LinearLayout
    private lateinit var companyNameText: TextView
    private lateinit var servicesText: TextView
    private lateinit var navButton: Button
    private lateinit var closeButton: ImageView
    private lateinit var zoomInButton: ImageView
    private lateinit var zoomOutButton: ImageView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationButton: ImageView

    private lateinit var searchInput: EditText
    private lateinit var chipGroup: ChipGroup
    private lateinit var chipAll: Chip
    private lateinit var chipSto: Chip
    private lateinit var chipTire: Chip
    private lateinit var chipBodywork: Chip
    private lateinit var chipPound: Chip
    private var userLocation: Point? = null
    private var userPlacemark: PlacemarkMapObject? = null
    private val listeners = mutableListOf<MapObjectTapListener>()

    private var currentQuery = ""
    private val selectedTypes = mutableSetOf<PointType>()

    private val locations = listOf(

        LocationData(
            point = Point(59.890000, 30.375000),
            type = PointType.POUND,
            companyName = "Штрафстоянка на Салова",
            services = listOf(
                "Спецстоянка",
                "Эвакуированные автомобили",
                "Санкт-Петербург, ул. Салова, 17"
            )
        ),
        LocationData(
            point = Point(59.985500, 30.255000),
            type = PointType.POUND,
            companyName = "Штрафстоянка на Школьной",
            services = listOf(
                "Спецстоянка",
                "Хранение эвакуированных ТС",
                "Санкт-Петербург, ул. Школьная, 39"
            )
        ),
        LocationData(
            point = Point(59.891800, 30.330500),
            type = PointType.POUND,
            companyName = "Штрафстоянка на Заставской",
            services = listOf(
                "Спецстоянка",
                "Выдача эвакуированных автомобилей",
                "Санкт-Петербург, Заставская ул., 4"
            )
        ),

        LocationData(
            point = Point(59.990899, 30.205789),
            type = PointType.STO,
            companyName = "СТО на Савушкина",
            services = listOf(
                "Ремонт автомобилей",
                "Техническое обслуживание",
                "Санкт-Петербург, ул. Савушкина, 119 к.4"
            )
        ),
        LocationData(
            point = Point(59.852971, 30.321510),
            type = PointType.STO,
            companyName = "СТО на Дунайском",
            services = listOf(
                "Диагностика",
                "ТО и ремонт подвески",
                "Санкт-Петербург, Дунайский пр., 15 к.2"
            )
        ),
        LocationData(
            point = Point(59.800215, 30.325588),
            type = PointType.STO,
            companyName = "СТО на Пулковском",
            services = listOf(
                "Техническое обслуживание",
                "Ремонт авто",
                "Санкт-Петербург, Пулковское шоссе, 43 к.1"
            )
        ),
        LocationData(
            point = Point(59.958820, 30.474233),
            type = PointType.STO,
            companyName = "СТО на Ириновском",
            services = listOf(
                "Обслуживание и ремонт",
                "Диагностика",
                "Санкт-Петербург, Ириновский пр., 10"
            )
        ),
        LocationData(
            point = Point(59.984421, 30.252184),
            type = PointType.BODYWORK,
            companyName = "СТО на Школьной",
            services = listOf(
                "Кузовной ремонт",
                "Покраска и полировка",
                "Санкт-Петербург, Школьная, 73А"
            )
        ),
        LocationData(
            point = Point(59.896421, 30.411287),
            type = PointType.STO,
            companyName = "СТО на Седова",
            services = listOf(
                "Ремонт автомобилей",
                "Шиномонтаж",
                "Санкт-Петербург, ул. Седова, 11АБ"
            )
        ),
        LocationData(
            point = Point(60.037412, 30.347918),
            type = PointType.TIRE,
            companyName = "Колесо2 Северный",
            services = listOf(
                "Шиномонтаж 24/7",
                "Балансировка колес",
                "Санкт-Петербург, Северный пр., 32"
            )
        ),
        LocationData(
            point = Point(59.964928, 30.487602),
            type = PointType.STO,
            companyName = "Сервис на Коммуны",
            services = listOf(
                "Авторемонт",
                "Техническое обслуживание",
                "Санкт-Петербург, ул. Коммуны, 16"
            )
        ),
        LocationData(
            point = Point(59.873756, 30.378941),
            type = PointType.TIRE,
            companyName = "Шинсервис Фучика",
            services = listOf(
                "Шиномонтаж",
                "Балансировка",
                "Санкт-Петербург, ул. Фучика, 8"
            )
        ),
        LocationData(
            point = Point(59.919460, 30.483250),
            type = PointType.TIRE,
            companyName = "Автостолица Дальневосточный",
            services = listOf(
                "Шиномонтаж",
                "ТО и мелкий ремонт",
                "Санкт-Петербург, Дальневосточный пр., 18А"
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.map_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MapKitFactory.initialize(requireActivity())

        locationButton = view.findViewById(R.id.location_button)
        mapView = view.findViewById(R.id.mapview)
        infoContainer = view.findViewById(R.id.info_container)
        companyNameText = view.findViewById(R.id.company_name)
        servicesText = view.findViewById(R.id.services)
        navButton = view.findViewById(R.id.nav_button)
        closeButton = view.findViewById(R.id.close_button)
        zoomInButton = view.findViewById(R.id.zoom_in_button)
        zoomOutButton = view.findViewById(R.id.zoom_out_button)
        chipPound = view.findViewById(R.id.chip_pound)
        searchInput = view.findViewById(R.id.search_input)
        chipGroup = view.findViewById(R.id.category_chip_group)
        chipAll = view.findViewById(R.id.chip_all)
        chipSto = view.findViewById(R.id.chip_sto)
        chipTire = view.findViewById(R.id.chip_tire)
        chipBodywork = view.findViewById(R.id.chip_bodywork)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mapView.map.move(
            CameraPosition(Point(59.934280, 30.335099), 10f, 0f, 0f)
        )

        setupFilters()
        renderMarkers(locations)

        closeButton.setOnClickListener {
            infoContainer.visibility = View.GONE
        }

        zoomInButton.setOnClickListener {
            mapView.map.move(
                mapView.map.cameraPosition.let {
                    CameraPosition(it.target, it.zoom + 1f, it.azimuth, it.tilt)
                }
            )
        }

        zoomOutButton.setOnClickListener {
            mapView.map.move(
                mapView.map.cameraPosition.let {
                    CameraPosition(it.target, it.zoom - 1f, it.azimuth, it.tilt)
                }
            )
        }

        locationButton.setOnClickListener {
            requestUserLocation()
        }
    }

    private fun setupFilters() {
        chipAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chipSto.isChecked = false
                chipTire.isChecked = false
                chipBodywork.isChecked = false
                selectedTypes.clear()
                applyFilters()
            }
        }

        val categoryListener = { _: android.widget.CompoundButton, _: Boolean ->
            if (chipSto.isChecked || chipTire.isChecked || chipBodywork.isChecked || chipPound.isChecked) {
                chipAll.isChecked = false
            } else {
                chipAll.isChecked = true
            }

            selectedTypes.clear()
            if (chipSto.isChecked) selectedTypes.add(PointType.STO)
            if (chipTire.isChecked) selectedTypes.add(PointType.TIRE)
            if (chipBodywork.isChecked) selectedTypes.add(PointType.BODYWORK)
            if (chipPound.isChecked) selectedTypes.add(PointType.POUND)

            applyFilters()
        }

        chipSto.setOnCheckedChangeListener(categoryListener)
        chipTire.setOnCheckedChangeListener(categoryListener)
        chipBodywork.setOnCheckedChangeListener(categoryListener)
        chipPound.setOnCheckedChangeListener(categoryListener)

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentQuery = s?.toString()?.trim().orEmpty()
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun applyFilters() {
        val filtered = locations.filter { location ->
            val matchesType = selectedTypes.isEmpty() || location.type in selectedTypes

            val query = currentQuery.lowercase()
            val matchesQuery = query.isBlank() ||
                    location.companyName.lowercase().contains(query) ||
                    location.services.any { it.lowercase().contains(query) }

            matchesType && matchesQuery
        }

        infoContainer.visibility = View.GONE
        renderMarkers(filtered)
    }

    private fun renderMarkers(items: List<LocationData>) {
        val mapObjects = mapView.map.mapObjects
        mapObjects.clear()
        listeners.clear()

        items.forEach { locationData ->
            val markerRes = when (locationData.type) {
                PointType.TIRE -> R.drawable.ic_baseline_tire_repair_24
                PointType.STO -> R.drawable.ic_baseline_car_crash_24
                PointType.BODYWORK -> R.drawable.yandex_car_24
                PointType.POUND -> android.R.drawable.ic_menu_mylocation
            }

            val bitmap = getBitmapFromVectorDrawable(requireContext(), markerRes)

            val placemark = mapObjects.addPlacemark(
                locationData.point,
                ImageProvider.fromBitmap(bitmap)
            )

            val listener = getPlaceMarkTapListener(locationData)
            listeners.add(listener)
            placemark.addTapListener(listener)
        }

        userLocation?.let { point ->
            showUserLocationMarker(point)
        }
    }

    private fun showInfo(locationData: LocationData) {
        companyNameText.text = locationData.companyName
        servicesText.text = locationData.services.joinToString("\n")
        navButton.setOnClickListener {
            openYandexNavigation(locationData.point)
        }
        infoContainer.visibility = View.VISIBLE
    }

    private fun getPlaceMarkTapListener(locationData: LocationData): MapObjectTapListener {
        return MapObjectTapListener { _, _ ->
            showInfo(locationData)
            true
        }
    }

    private fun showUserLocationMarker(point: Point) {
        if (userPlacemark == null) {
            val bitmap = getBitmapFromVectorDrawable(
                requireContext(),
                R.drawable.ic_baseline_star_24
            )

            userPlacemark = mapView.map.mapObjects.addPlacemark().apply {
                geometry = point
                setIcon(ImageProvider.fromBitmap(bitmap))
            }
        } else {
            userPlacemark?.geometry = point
        }
    }

    private fun requestUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val point = Point(location.latitude, location.longitude)
                    userLocation = point
                    showUserLocationMarker(point)

                    mapView.map.move(
                        CameraPosition(point, 15f, 0f, 0f)
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось определить местоположение",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Ошибка получения геолокации",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun openYandexNavigation(destination: Point) {
        val latTo = destination.latitude
        val lonTo = destination.longitude

        val mapsUri = userLocation?.let { userPoint ->
            Uri.parse(
                "yandexmaps://maps.yandex.ru/?rtext=" +
                        "${userPoint.latitude},${userPoint.longitude}~$latTo,$lonTo&rtt=auto"
            )
        } ?: Uri.parse(
            "yandexmaps://maps.yandex.ru/?pt=$lonTo,$latTo&z=16"
        )

        val naviUri = userLocation?.let { userPoint ->
            Uri.parse(
                "yandexnavi://build_route_on_map" +
                        "?lat_from=${userPoint.latitude}" +
                        "&lon_from=${userPoint.longitude}" +
                        "&lat_to=$latTo" +
                        "&lon_to=$lonTo"
            )
        } ?: Uri.parse(
            "yandexnavi://build_route_on_map?lat_to=$latTo&lon_to=$lonTo"
        )

        try {
            val mapsIntent = Intent(Intent.ACTION_VIEW, mapsUri).apply {
                setPackage("ru.yandex.yandexmaps")
            }
            startActivity(mapsIntent)
            return
        } catch (_: Exception) {
        }

        try {
            val naviIntent = Intent(Intent.ACTION_VIEW, naviUri).apply {
                setPackage("ru.yandex.yandexnavi")
            }
            startActivity(naviIntent)
            return
        } catch (_: Exception) {
        }

        val webUrl = userLocation?.let { userPoint ->
            "https://yandex.ru/maps/?rtext=${userPoint.latitude},${userPoint.longitude}~$latTo,$lonTo&rtt=auto"
        } ?: "https://yandex.ru/maps/?pt=$lonTo,$latTo&z=16"

        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)))
        } catch (_: Exception) {
            Toast.makeText(requireContext(), "Не удалось открыть маршрут", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listeners.clear()
    }
}


enum class PointType {
    TIRE, STO, BODYWORK, POUND
}

data class LocationData(
    val point: Point,
    val type: PointType,
    val companyName: String,
    val services: List<String>
)

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
        ?: throw IllegalArgumentException("Drawable not found")
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
   }