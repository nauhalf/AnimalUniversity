package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.password

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.alertLoading
import kotlinx.android.synthetic.main.activity_edit_password.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EditPasswordActivity : AppCompatActivity(), EditPasswordView {

    private lateinit var loading : AlertDialog
    private lateinit var presenter : EditPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)
        setUp()
    }

    override fun showError(inputCode: Int, errorCode: Int) {
        when (inputCode) {
            1 -> {
                txt_layout_old_password.error = when (errorCode) {
                    1 -> {
                        getString(R.string.error_empty)
                    }
                    2 -> {
                        getString(R.string.invalid_password)
                    }
                    else -> null
                }
            }
            2 -> {
                txt_layout_new_password.error = when (errorCode) {
                    1 -> {
                        getString(R.string.error_empty)
                    }
                    2 -> {
                        getString(R.string.password_min)
                    }
                    else -> null
                }
            }
            3 -> {
                txt_layout_re_password.error = when (errorCode) {
                    1 -> {
                        getString(R.string.error_empty)
                    }
                    2 -> {
                        getString(R.string.not_same_password)
                    }
                    else -> null
                }
            }
        }
    }

    override fun hideError(inputCode: Int) {
        when(inputCode){
            1 -> txt_layout_old_password.error = null
            2 -> txt_layout_new_password.error = null
            3 -> txt_layout_re_password.error = null
        }
    }

    override fun getContext(): Context = this

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.hide()
    }

    override fun close() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUp(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        loading = alertLoading()

        presenter = EditPasswordPresenter(this)

        btn_save.onClick {
            val old = presenter.validate(1, edit_old_password.text.toString())
            val new = presenter.validateNewPassword(edit_new_password.text.toString(), edit_re_password.text.toString())

            if(old && new){
                presenter.update(edit_new_password.text.toString())
            }
        }

        btn_cancel.onClick {
            finish()
        }
    }


}
