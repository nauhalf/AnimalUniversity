package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.adapter.GpaAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var semester : MutableList<String> = mutableListOf()
    lateinit var adapter : GpaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup(){
        setSupportActionBar(toolbar as Toolbar)
        semester.add("1")
        semester.add("2")
        semester.add("3")
        semester.add("4")
        semester.add("5")
        semester.add("6")
        semester.add("7")
        semester.add("8")
        adapter = GpaAdapter(semester){
            toast(it).show()
        }
        recycle_gpa.layoutManager = LinearLayoutManager(this)
        recycle_gpa.adapter = adapter
    }
}
