package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.gpa.GpaActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.login.LoginActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main.adapter.GpaAdapter
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.ProfileActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.ImageSaver
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.invisible
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), MainView {

    private var listGpa: MutableList<Gpa> = mutableListOf()
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var rootView: LinearLayout
    private lateinit var adapter: GpaAdapter
    private lateinit var presenter: MainPresenter
    private val format = DecimalFormat("#.##")
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var imageSaver: ImageSaver
    private lateinit var navigationView: NavigationView
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

        drawer = drawer_layout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar as Toolbar, R.string.drawer_open, R.string.drawer_close)
        imageSaver = ImageSaver(this)
        navigationView = navigation_view
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    drawer.closeDrawers()
                    navigationView.menu.getItem(0).isChecked = false
                    startActivity<ProfileActivity>()
                    false
                }
                R.id.logout -> {
                    navigationView.menu.getItem(1).isChecked = false
                    presenter.logout()
                    true
                }
                else -> false
            }
        }

        presenter = MainPresenter(this)
        shimmer = shimmer_main_activity
        rootView = root_view
        shimmer.startShimmer()


        adapter = GpaAdapter(listGpa) {
            it.lock?.let { itLock ->
                if (itLock) {
                    Snackbar.make(
                            root_layout,
                            getString(R.string.disable_edit_message),
                            Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    startActivityForResult<GpaActivity>(1,
                            "idGpa" to it.id,
                            "semester" to it.semester,
                            "value" to it.value,
                            "isEdit" to true)
                }
            }
        }


        swipe.setColorSchemeColors((ContextCompat.getColor(this, R.color.colorAccent)),
                ContextCompat.getColor(this, android.R.color.holo_green_light),
                ContextCompat.getColor(this, android.R.color.holo_orange_light),
                ContextCompat.getColor(this, R.color.colorPrimary))


        recycle_gpa.layoutManager = object : LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        recycle_gpa.adapter = adapter

        btn_add_semester.onClick {
            startActivityForResult<GpaActivity>(1,
                    "semester" to adapter.getLatestSemester()?.plus(1),
                    "isEdit" to false)
        }

        presenter.fetchData()

        swipe?.setOnRefreshListener {
            presenter.fetchData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            presenter.fetchData()
        }
    }

    override fun hideLoading() {
        shimmer.stopShimmer()
        shimmer.invisible()
        rootView.visible()

    }

    override fun showLoading() {
        shimmer.startShimmer()
        shimmer.visible()
        rootView.visible()
    }


    override fun getContext(): Context {
        return this
    }

    override fun loadData(s: Student, list: List<Gpa>?) {
        swipe.isRefreshing = false
        txt_student_name.text = s.name
        txt_student_nim.text = s.nim
        txt_faculty.text = s.faculty
        txt_major.text = s.major
        txt_target_ipk_value.text = s.targetGpa?.let { format.format(it) } ?: run { "N/A" }


        txt_ipk_saya_value.text = format.format(adapter.getCurrentGpa())
        val enable = adapter.getLatestSemester()?.let {
            it < 8
        }

        enable?.let {
            btn_add_semester.isEnabled = it
        }


        navigationView.findViewById<TextView>(R.id.drawer_txt_name).text = s.name
        navigationView.findViewById<TextView>(R.id.drawer_txt_nim).text = s.nim

        s.image?.let {
            val img = imageSaver.setFileName(it).load()
            navigationView.findViewById<CircleImageView>(R.id.drawer_img_photo).setImageBitmap(img)
            img_photo.setImageBitmap(img)
        } ?: run {
            navigationView.findViewById<CircleImageView>(R.id.drawer_img_photo).setImageResource(R.drawable.default_pic)
            img_photo.setImageResource(R.drawable.default_pic)
        }

        listGpa.clear()
        list?.let { listGpa.addAll(it) }
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        presenter.fetchData()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            return true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun logout() {
        startActivity<LoginActivity>()
        finish()
    }

    override fun openProfileAcitivty() {
        startActivity<ProfileActivity>()
    }
}
