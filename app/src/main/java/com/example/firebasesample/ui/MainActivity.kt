package com.example.firebasesample.ui


import android.os.Bundle
import com.example.firebasesample.R
import com.example.firebasesample.base.BaseActivity
import com.example.firebasesample.databinding.ActivityMainBinding
import com.example.firebasesample.ext.replaceFragment
import com.example.firebasesample.ui.chatlist.ChatListFragment
import com.example.firebasesample.ui.home.HomeFragment
import com.example.firebasesample.ui.mypage.MyPageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        with(binding) {
            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> replaceFragment(homeFragment, fragmentContainer.id)
                    R.id.chatList -> replaceFragment(chatListFragment, fragmentContainer.id)
                    R.id.myPage -> replaceFragment(myPageFragment, fragmentContainer.id)
                }
                true
            }
        }
    }
}