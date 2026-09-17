package com.example.mycar

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("51636299-14e3-49f9-8f7a-42d2f93fdd04")
        MapKitFactory.initialize(this)
    }
}