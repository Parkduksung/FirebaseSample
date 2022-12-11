package com.example.firebasesample.test

import android.os.Bundle
import androidx.activity.viewModels
import com.example.firebasesample.R
import com.example.firebasesample.base.BaseActivity
import com.example.firebasesample.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : BaseActivity<ActivityTestBinding>(R.layout.activity_test) {

    private val viewModel by viewModels<TestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.test()
    }
}