package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.targetgpa

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.alertLoading
import kotlinx.android.synthetic.main.activity_edit_target_gpa.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EditTargetGpaActivity : AppCompatActivity(), EditTargetGpaView {

    private lateinit var presenter: EditTargetGpaPresenter
    private lateinit var loading : AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_target_gpa)
        setUp()
    }

    override fun showError(errorCode: Int) {
        txt_layout_gpa.error = when (errorCode) {
            1 -> {
                getString(R.string.error_empty)
            }
            2 -> {
                getString(R.string.range_gpa)
            }
            else -> null
        }
    }

    override fun hideError() {
        txt_layout_gpa.error = null
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
        s.targetGpa?.let {
            edit_gpa.setText(it.toString())
        }
    }

    override fun hideLoading() {
        loading.dismiss()
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

    private fun setUp() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loading = alertLoading()

        presenter = EditTargetGpaPresenter(this)
        presenter.fetchData()

        btn_save.onClick {
            if (presenter.validate(edit_gpa.text.toString())) {
                presenter.update(edit_gpa.text.toString().toDouble())
            }
        }

        btn_cancel.onClick {
            finish()
        }
    }
}
