package com.example.gdmap.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.route.*
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.InputTipUtils
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import com.example.gdmap.utils.ToastUtils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_article_content.*
import kotlinx.android.synthetic.main.activity_article_content.toolBar
import kotlinx.android.synthetic.main.activity_timebus.*
import kotlinx.android.synthetic.main.activity_walk.*
import kotlinx.android.synthetic.main.fragment_map_bottom_sheet.*

class WalkActivity :BaseActivity(),TextWatcher
{
    private var mylat:Double?=null
    private var mylng:Double?=null
    private var ideallat:Double?=null
    private var ideallng:Double?=null
    private var cityName:String?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,toolBar)
        val intent=intent
        mylat=intent.getDoubleExtra("lat",0.0)
        mylng=intent.getDoubleExtra("lng",0.0)
        cityName=intent.getStringExtra("city")
        cityName?.showToast()
        initView()
    }

    private fun initView() {
        tv_activity_walk_my_place.text = cityName
        et_activity_walk_idea_place.addTextChangedListener(this)
        et_activity_walk_idea_place.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                ideallat = InputTipUtils.getTipList()?.get(p2)?.point?.latitude
                ideallng = InputTipUtils.getTipList()?.get(p2)?.point?.longitude
            }
        bt_activity_walk_glide.setOnClickListener {

                val intent = Intent(context, GudieActivity::class.java)
                intent.putExtra("lat", mylat)
                intent.putExtra("lng", mylng)
                intent.putExtra("endlat", ideallat)
                intent.putExtra("endlng", ideallng)
                startActivity(intent)
            }
    }
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (charSequence?.length!! < 0) {
            return
        } else {
            cityName?.let {
                InputTipUtils.setContext(et_activity_walk_idea_place, context).initInputTip(
                    charSequence.toString().trim(),
                    it
                )
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}