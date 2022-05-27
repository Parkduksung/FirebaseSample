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

    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_name)[position]
            tab.icon = resources.obtainTypedArray(R.array.array_tab_icon).getDrawable(position)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val list = listOf(HomeFragment(), ChatListFragment(), MyPageFragment())
        val pagerAdapter = FragmentPagerAdapter(list, this)
        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 4
            viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()
        }
    }
}

class FragmentPagerAdapter(
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]

}