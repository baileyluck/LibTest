package com.qianxinde.libtest.module.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.library.common.base.BaseActivity
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.ActivityDetailsBinding
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class DetailsActivity : BaseActivity<DetailsViewModel, ActivityDetailsBinding>() {

    companion object {
        val ID = "id"

        fun launch(context: Context, id: String) {
            context.startActivity(Intent().apply {
                putExtra(ID, id)
                setClass(context, DetailsActivity::class.java)
            })
        }
    }

    private lateinit var mId: String

    override fun getLayoutId() = R.layout.activity_details;

    override fun getReplaceView(): View = smartRefreshLayout

    override fun init(savedInstanceState: Bundle?) {
        mId = intent.getStringExtra(ID)
        ActionBarUtils.setCenterTitleText(toolbar, "测试")
        ActionBarUtils.setSupportActionBarWithBack(toolbar, null, View.OnClickListener {
            finish()
        })
        mViewModel.mDetails.observe(this, Observer {
            mBinding!!.detail = it
        })
        mViewModel.getHomeDetails(mId)
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout? = smartRefreshLayout

    override fun refreshData() {
        mViewModel.getHomeDetails(mId)
    }
}
