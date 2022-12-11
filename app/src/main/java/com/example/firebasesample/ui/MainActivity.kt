package com.example.firebasesample.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firebasesample.R
import com.example.firebasesample.base.BaseActivity
import com.example.firebasesample.databinding.ActivityMainBinding
import com.example.firebasesample.ui.chatlist.ChatListFragment
import com.example.firebasesample.ui.home.HomeFragment
import com.example.firebasesample.ui.mypage.MyPageFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
