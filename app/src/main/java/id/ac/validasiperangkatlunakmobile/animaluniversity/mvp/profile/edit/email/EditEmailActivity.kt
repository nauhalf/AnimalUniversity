package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.email

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.alertLoading
import kotlinx.android.synthetic.main.activity_edit_email.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EditEmailActivity : AppCompatActivity(), EditEmailView {

    private lateinit var loading: AlertDialog
    private lateinit var presenter: EditEmailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_email)
        setUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun showError(inputCode: Int, errorCode: Int) {
        when (inputCode) {
            1 -> {
                txt_layout_email.error = when (errorCode) {
                    1 -> {
                        getString(R.string.error_empty)
                    }
                    2 -> {
                        getString(R.string.invalid_email)
                    }
                    else -> null
                }
            }
            2 -> {
                txt_layout_password.error = when (errorCode) {
                    1 -> {
                        getString(R.string.error_empty)
                    }
                    2 -> {
                        getString(R.string.invalid_password)
                    }
                    else -> null
                }
            }
        }
    }

    override fun hideError(inputCode: Int) {
        when (inputCode) {
            1 -> txt_layout_email.error = null
            2 -> txt_layout_password.error = null
        }
    }

    override fun getContext(): Context = this

    override fun close(result: Int) {
        setResult(result)
        finish()
    }

    override fun showLoading() {
        loading.show()
    }

    override fun load(s: Student) {
        s.email?.let {
            edit_email.setText(it)
        }
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    private fun setUp() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        presenter = EditEmailPresenter(this)
        loading = alertLoading()

        presenter.fetchData()

        btn_save.onClick {
            val email = presenter.validate(1, edit_email.text.toString())

            val password = presenter.validate(2, edit_password.text.toString())

            if (email && password)
                presenter.update(edit_email.text.toString())

        }

        btn_cancel.onClick {
            finish()
        }
    }
}
