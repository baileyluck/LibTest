package com.qianxinde.libtest.module.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.databinding.ViewDataBinding
import com.android.aachulk.consts.Constant
import com.blankj.utilcode.util.SPUtils
import com.gyf.immersionbar.ImmersionBar
import com.library.common.base.BaseActivity
import com.qianxinde.libtest.R
import com.qianxinde.libtest.base.CommonViewModel
import com.qianxinde.libtest.module.login.LoginActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity<CommonViewModel, ViewDataBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, StartActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_start

    override fun getReplaceView(): View = activity_start

    override fun init(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .statusBarColor(R.color.white)
            .init()
        mCountDownTimer.start()
    }

    private val mCountDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onFinish() {
            val token = SPUtils.getInstance().getString(Constant.TOKEN)
            if (!TextUtils.isEmpty(token)) {
                MainActivity.launch(mContext)
            } else {
                LoginActivity.launch(mContext)
            }
            finish()
        }

        override fun onTick(millisUntilFinished: Long) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer.cancel()
    }
}