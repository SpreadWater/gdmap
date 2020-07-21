package com.example.gdmap

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.adapter.FragmentsAdapter
import com.example.gdmap.ui.fragment.MapFragment
import com.example.gdmap.ui.fragment.MyFragment
import com.example.gdmap.ui.fragment.SeeFragment
import com.example.gdmap.utils.ToastUtils.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnPageChangeListener{
    private lateinit var fragmentsAdapter: FragmentsAdapter
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(MapFragment())
        fragmentList.add(SeeFragment())
        fragmentList.add(MyFragment())
        fragmentsAdapter = FragmentsAdapter(fragmentList, supportFragmentManager, 3)
        viewpager.adapter = fragmentsAdapter
        viewpager.addOnPageChangeListener(this)
        bt_nv_menu.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.bt_nav_map -> viewpager.currentItem = 0
                R.id.bt_nav_surrounding -> viewpager.currentItem = 1
                R.id.bt_nav_me -> viewpager.currentItem = 2
                else -> "error".showToast()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        bt_nv_menu.menu.getItem(position).isChecked = true
    }
}