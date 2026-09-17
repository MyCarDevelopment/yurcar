package com.example.mycar.ui.checkfines

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.mycar.BaseActivity
import com.example.mycar.databinding.FragmentSellBinding


class FinesFragment : BaseActivity() {

    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckFinesFragment()
        }
    }
}
