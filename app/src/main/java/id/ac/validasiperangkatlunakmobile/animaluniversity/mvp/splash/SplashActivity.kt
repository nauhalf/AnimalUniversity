package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<LoginActivity>()
        finish()
    }
}
