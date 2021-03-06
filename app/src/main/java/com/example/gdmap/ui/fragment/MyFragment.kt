package com.example.gdmap.ui.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.base.BaseFragment
import com.example.gdmap.ui.activity.*
import com.example.gdmap.utils.*
import kotlinx.android.synthetic.main.fragment_me.*
import top.limuyang2.photolibrary.LPhotoHelper


class MyFragment : BaseFragment(), View.OnClickListener {
    companion object {
        const val TAKE_PHOTO = 1
    }

    private var saveData: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun initView() {
        saveData = context?.getSharedPreferences("userdata", MODE_PRIVATE)
        bt_fragment_me_data.setOnClickListener(this)
        bt_fragment_me_question.setOnClickListener(this)
        bt_fragment_me_set.setOnClickListener(this)
        bt_fragment_collect.setOnClickListener(this)
        iv_fragment_me_user_avator.setOnClickListener(this)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_day, bt_fragment_collect, 0)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_set, bt_fragment_me_set, 0)
        AddIconImage.setImageViewToButton(
            R.mipmap.fragment_me_bt_question,
            bt_fragment_me_question,
            0
        )
        AddIconImage.setImageViewToButton(R.mipmap.acticity_login_name, bt_fragment_me_data, 0)
    }

    override fun initClick() {
        tv_back_login.setOnClickListener {
            changeToActivity(LoginActivity())
            activity?.finish()
        }
    }

    override fun initData() {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.bt_fragment_me_set-> changeToActivity(ProductActivity())
            R.id.bt_fragment_me_data -> changeToActivity2(SetDataActivity())
            R.id.bt_fragment_collect -> changeToActivity(CollectActivity())
            R.id.bt_fragment_me_question -> changeToActivity(TipsActivity())
            R.id.iv_fragment_me_user_avator -> {
                ImageSelectutils.selectImageFromAlbum(1, this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO -> {
                    val selectedPhotos = LPhotoHelper.getSelectedPhotos(data).map {
                        it.toString()
                    }
                    Glide.with(iv_fragment_me_user_avator).load(selectedPhotos[0])
                        .into(iv_fragment_me_user_avator)
                }
            }
        }
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }

    private fun changeToActivity2(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        tv_fragment_me_user_data.text = saveData?.getString("data", "")
        tv_fragment_me_user_name.text = saveData?.getString("name", "")
        super.onStart()
    }
}