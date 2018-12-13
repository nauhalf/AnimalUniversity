package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.addgpa

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import kotlinx.android.synthetic.main.activity_add_gpa.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AddGpaActivity : AppCompatActivity(), AddGpaView {
    private var semester: Int? = null
    private lateinit var presenter: AddGpaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gpa)
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

        presenter = AddGpaPresenter(this)
        semester = intent.getIntExtra("semester", 0)
        var s = "Semester $semester"
        txt_add_semester.text = s

        btn_save.onClick {
            semester?.let {
                if (presenter.validate(edit_gpa.text.toString())) {
                    presenter.save(it, edit_gpa.text.toString().toDouble())
                }
            }
        }

        edit_gpa.setOnFocusChangeListener { v, hasFocus -> if (!hasFocus) presenter.validate((v as TextInputEditText).text.toString()) }

    }




}
