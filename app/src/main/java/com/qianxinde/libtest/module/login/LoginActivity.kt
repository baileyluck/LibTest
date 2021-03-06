package com.qianxinde.libtest.module.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.android.aachulk.consts.Constant
import com.android.aachulk.consts.LiveEventBusKey
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SPUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.library.common.base.BaseActivity
import com.qianxinde.libtest.R
import com.qianxinde.libtest.databinding.ActivityLoginBinding
import com.qianxinde.libtest.module.common.MainActivity
import com.qianxinde.libtest.module.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import utils.ActionBarUtils

/**
 * @author yangbw
 * @date
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent().apply {
                setClass(context, LoginActivity::class.java)
            })
        }
    }

    override fun getLayoutId() = R.layout.activity_login;

    override fun getReplaceView(): View = activity_login

    override fun init(savedInstanceState: Bundle?) {
        ActionBarUtils.setCenterTitleText(toolbar, "登录")

        mViewModel.mUser.observe(this, Observer {
            it.let {
                SPUtils.getInstance().put(Constant.TOKEN,it.token)
                MainActivity.launch(mContext)
                showToast("登录成功")
                finish()
            }

        })
        mBinding?.run {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                if (username.isBlank()) {
                    showToast("请输入用户名")
                    return@setOnClickListener
                }
                val password = etPassword.text.toString()
                if (password.isBlank()) {
                    showToast("请输入密码")
                    return@setOnClickListener
                }
                mViewModel.login(username, password)
            }
            tvRegister.setOnClickListener {
                RegisterActivity.launch(mContext)
            }
        }
        LiveEventBus.get(LiveEventBusKey.REGISTER_SUC, UserData::class.java)
            .observe(this, Observer<UserData?> {
                it?.let {
                    mBinding!!.etUsername.setText(it.username)
                    mBinding!!.etPassword.setText(it.password)
                }
            })

        PermissionUtils.permission(*PermissionUtils.getPermissions().toTypedArray())
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {

                }

                override fun onDenied(
                    forever: MutableList<String>, denied: MutableList<String>
                ) {

                }

            })
            .request()
    }

}
