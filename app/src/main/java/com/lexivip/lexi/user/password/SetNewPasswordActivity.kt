package com.lexivip.lexi.user.password
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.basemodule.tools.ToastUtil
import com.basemodule.tools.WaitingDialog
import com.basemodule.ui.BaseActivity
import com.lexivip.lexi.R
import kotlinx.android.synthetic.main.acticity_set_new_password.*

/**
 *
 * 忘记密码
 */
class SetNewPasswordActivity : BaseActivity(), View.OnClickListener, SetNewPasswordContract.View {

    private val dialog: WaitingDialog? by lazy { WaitingDialog(this) }

    private var showPassword: Boolean = false
    private var showPassword1: Boolean = false

    private lateinit var presenter: SetNewPasswordPresenter

    override val layout: Int = R.layout.acticity_set_new_password

    private var phone:String = ""

    override fun getIntentData() {
       if (intent.hasExtra(ForgetPasswordActivity::class.java.simpleName)){
            phone = intent.getStringExtra(ForgetPasswordActivity::class.java.simpleName)
       }
    }

    override fun initView() {
        presenter = SetNewPasswordPresenter(this)
    }

    override fun installListener() {
        button.setOnClickListener(this)
        imageViewShow.setOnClickListener(this)
        imageViewShow1.setOnClickListener(this)
    }


    override fun setPresenter(presenter: SetNewPasswordContract.Presenter?) {
        setPresenter(presenter)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            //更新密码
            R.id.button -> presenter.updateNewPassword(phone,etPassword.text.toString(),etPassword1.text.toString())

            R.id.imageViewShow -> {
                if (showPassword) {
                    imageViewShow.setImageResource(R.mipmap.icon_hidden_password)
                    showPassword = false
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    imageViewShow.setImageResource(R.mipmap.icon_show_password)
                    showPassword = true
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }

            R.id.imageViewShow1 -> {
                if (showPassword1) {
                    imageViewShow1.setImageResource(R.mipmap.icon_hidden_password)
                    showPassword1 = false
                    etPassword1.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    imageViewShow1.setImageResource(R.mipmap.icon_show_password)
                    showPassword1 = true
                    etPassword1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
            }


        }
    }

    override fun showLoadingView() {
        dialog?.show()
    }

    override fun dismissLoadingView() {
        dialog?.dismiss()
    }

    override fun showError(s: String) {
        ToastUtil.showError(s)
    }

    override fun goPage() {
        finish()
    }

    override fun showInfo(string: String) {
        ToastUtil.showInfo(string)
    }

}