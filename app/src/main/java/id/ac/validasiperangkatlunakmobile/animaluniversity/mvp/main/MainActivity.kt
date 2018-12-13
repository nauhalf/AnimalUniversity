package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.addgpa.AddGpaActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.adapter.GpaAdapter
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.invisible
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivityForResult
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), MainView {

    private var listGpa: MutableList<Gpa> = mutableListOf()
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var constraintShow: ConstraintLayout
    private lateinit var adapter: GpaAdapter
    private lateinit var presenter: MainPresenter
    private val format = DecimalFormat("#.##")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        presenter = MainPresenter(this)
        shimmer = shimmer_main_activity
        shimmer.startShimmer()
        constraintShow = constraint_show

        presenter.fetchData()

        adapter = GpaAdapter(listGpa) {

        }

        recycle_gpa.layoutManager = LinearLayoutManager(this)
        recycle_gpa.adapter = adapter

        btn_add_semester.onClick {
            startActivityForResult<AddGpaActivity>(1, "semester" to adapter.getLatestSemester()?.plus(1))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            presenter.fetchData()
        }
    }

    override fun hideLoading() {
        shimmer.stopShimmer()
        shimmer.invisible()
        constraintShow.visible()
    }

    override fun showLoading() {
        shimmer.startShimmer()
        shimmer.visible()
        constraintShow.visible()
    }

    override fun getContext(): Context {
        return this
    }

    override fun loadData(s: Student, list: List<Gpa>) {

        txt_student_name.text = s.name
        txt_student_nim.text = s.nim
        txt_target_ipk_value.text = s.targetGpa?.let { format.format(it) } ?: run { "N/A" }

        listGpa.clear()
        listGpa.addAll(list)
        adapter.notifyDataSetChanged()

        txt_ipk_saya_value.text = format.format(adapter.getCurrentGpa())
        btn_add_semester.isEnabled = adapter.getLatestSemester()?.let { it < 8 }!!
    }
}
