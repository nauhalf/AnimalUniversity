package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.address

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.alertLoading
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EditAddressActivity : AppCompatActivity(), EditAddressView {

    private lateinit var loading : AlertDialog
    private lateinit var presenter : EditAddressPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
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


    override fun getContext(): Context = this

    override fun close(result: Int) {
        setResult(result)
        finish()
    }

    override fun showLoading() {
        loading.show()
    }

    override fun load(s: Student) {
        edit_address.setText(s.address)
    }

    override fun hideLoading() {
        loading.hide()
    }

    fun setUp(){
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loading = alertLoading()
        presenter = EditAddressPresenter(this)
        presenter.fetchData()

        btn_save.onClick {
            presenter.update(edit_address.text.toString())
        }

        btn_cancel.onClick {
            finish()
        }
    }
}
