package com.example.mycar

import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.mycar.ui.map.MapFragment

class MapDesignPreviewActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(16, 18, 20)
        window.navigationBarColor = android.graphics.Color.rgb(16, 18, 20)
        setContentView(FrameLayout(this).apply { id = R.id.nav_host_fragment_activity_main })
        if (savedInstanceState == null) supportFragmentManager.commit {
            replace(R.id.nav_host_fragment_activity_main, MapFragment().apply {
                arguments = bundleOf("selected" to intent.getStringExtra("selected"))
            })
        }
    }
}
