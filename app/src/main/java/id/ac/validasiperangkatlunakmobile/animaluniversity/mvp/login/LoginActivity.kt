package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.login

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.MainActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.alertLoading
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.decodeSampledBitmapFromResource
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity(), LoginView{

    private lateinit var loading : AlertDialog
    private lateinit var presenter : LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUp()

    }

    private fun setUp(){
        presenter = LoginPresenter(this)
        presenter.isLoggedIn()
        loading = alertLoading()
        img_logo_login.setImageBitmap(
                decodeSampledBitmapFromResource(resources, R.drawable.logo_login, 920, 300)
        )

        btn_login.onClick {
            presenter.login(edit_nim.text.toString(), edit_password.text.toString())
        }
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun getContext(): Context = this

    override fun showLoginFailDialog() {
        alert(getString(R.string.error_login)){
            okButton { dialog ->  dialog.dismiss() }
        }.show()
    }

    override fun openMainActivity() {
        startActivity<MainActivity>()
        finish()
    }
}
