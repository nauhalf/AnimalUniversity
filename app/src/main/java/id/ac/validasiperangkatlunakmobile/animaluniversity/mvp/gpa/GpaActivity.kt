package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.gpa

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import kotlinx.android.synthetic.main.activity_gpa.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class GpaActivity : AppCompatActivity(), GpaView {
    private lateinit var presenter: GpaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpa)
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

    private fun setUp() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter = GpaPresenter(this)
        var idGpa: Long? = null
        val isEdit = intent.getBooleanExtra("isEdit", false)
        val semester : Int? = intent.getIntExtra("semester", 0)
        if (!isEdit) {
            supportActionBar?.title = getString(R.string.tambah_ips_semester)
        } else {
            idGpa = intent.getLongExtra("idGpa", 0)
            edit_gpa.setText(intent.getDoubleExtra("value", 0.0).toString())
            supportActionBar?.title = getString(R.string.ubah_ips_semester)
        }

        var s = "IPS Semester $semester"
        txt_layout_gpa.hint = s

        btn_save.onClick {
            if(isEdit){
                idGpa?.let { g1 ->
                    if(presenter.validate(edit_gpa.text.toString())){
                        presenter.update(g1, edit_gpa.text.toString().toDouble())
                    }
                }

            }
            else {
                semester?.let { s1 ->
                    if (presenter.validate(edit_gpa.text.toString())) {
                        presenter.insert(s1, edit_gpa.text.toString().toDouble())
                    }
                }
            }
        }

        btn_cancel.onClick {
            finish()
        }

        edit_gpa.setOnFocusChangeListener { v, hasFocus -> if (!hasFocus) presenter.validate((v as TextInputEditText).text.toString()) }

    }


}
