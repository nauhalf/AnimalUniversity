package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.onClick {
            startActivity<MainActivity>()
        }

    }
}
