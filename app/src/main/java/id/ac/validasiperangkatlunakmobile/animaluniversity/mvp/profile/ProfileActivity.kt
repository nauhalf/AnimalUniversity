package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.google.android.material.snackbar.Snackbar
import com.yalantis.ucrop.UCrop
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.fragment.bottomsheets.BottomSheetPhotoProfile
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.address.EditAddressActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.email.EditEmailActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.password.EditPasswordActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.targetgpa.EditTargetGpaActivity
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.ImageSaver
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.alertLoading
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.getFileName
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.getUriFromPath
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import java.text.DecimalFormat


class ProfileActivity : AppCompatActivity(), ProfileView, BottomSheetPhotoProfile.BottomSheetPhotoProfileListener {

    private lateinit var presenter: ProfilePresenter
    private lateinit var loading: AlertDialog
    private lateinit var imageSaver: ImageSaver
    private lateinit var bottomDialog : BottomSheetPhotoProfile
    private val format = DecimalFormat("#.##")

    companion object {
        const val RequestCode = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
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

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun getContext(): Context = this

    override fun load(s: Student) {
        txt_name.text = s.name
        txt_nim.text = s.nim
        txt_faculty.text = s.faculty
        txt_major.text = s.major
        txt_target_gpa.text = s.targetGpa?.let { format.format(it) } ?: run { "N/A" }
        txt_email.text = s.email?.let { it } ?: run { "-" }
        txt_address.text = s.address?.let { it } ?: run { "-" }
        s.image?.let {
            img_photo.setImageBitmap(imageSaver.setFileName(it).load())
        } ?: run {
            img_photo.setImageResource(R.drawable.default_pic)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            presenter.fetchData()
        } else if (requestCode == RequestCode && resultCode == Activity.RESULT_OK) {
            val images = ImagePicker.getFirstImageOrNull(data)
            if (images != null) {
                UCrop.of(images.path.getUriFromPath(this),
                        ImageSaver(this)
                                .setFileName(ImageSaver.generateFileName())
                                .setDirectoryName("avatar_images")
                                .getUri())
                        .withAspectRatio(1f, 1f)
                        .withMaxResultSize(250, 250)
                        .withOptions(
                                UCrop.Options().apply {
                                    setToolbarColor(ContextCompat.getColor(this@ProfileActivity, R.color.colorPrimary))
                                    setActiveWidgetColor(ContextCompat.getColor(this@ProfileActivity, R.color.colorPrimary))
                                }
                        )
                        .start(this)
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = data?.let { UCrop.getOutput(it) }
            presenter.update(resultUri?.getFileName(this))
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = data?.let { UCrop.getError(it) }
            Log.d("error : ", "error crop ", cropError)
        }
    }

    override fun reload(result: Int) {
        if (result == 1)
            presenter.fetchData()
    }

    override fun showDialog(photo: String?) {
        photo?.let {
            bottomDialog = BottomSheetPhotoProfile.newInstance(it)
            bottomDialog.listener = this
            bottomDialog.show(supportFragmentManager, "bottom_sheet_photo_dialog")
        } ?: run {
            setUpPicker()
        }

    }

    override fun onRemoveListener(photo: String) {
        presenter.removePhoto(photo)
        bottomDialog.dismiss()
        reload(1)
    }

    override fun onSelectListener() {
        setUpPicker()
        bottomDialog.dismiss()
    }

    override fun snackbar(s: String) {
        Snackbar.make(
                root_layout,
                s,
                Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setUp() {
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        loading = alertLoading()

        presenter = ProfilePresenter(this)
        imageSaver = ImageSaver(this)
        presenter.fetchData()

        setUpClick()
    }

    private fun setUpPicker() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(false) // folder mode (false by default)
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .includeVideo(false) // Show video on image picker
                .single() // single mode
                .toolbarDoneButtonText("Pilih")
                .showCamera(true) // show camera or not (true by default)
                .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(false) // disabling log
                .start(RequestCode) // start image picker activity with request code
    }

    private fun setUpClick() {
        constraint_target_gpa.onClick {
            startActivityForResult<EditTargetGpaActivity>(1)
        }

        constraint_email.onClick {
            startActivityForResult<EditEmailActivity>(1)
        }

        constraint_address.onClick {
            startActivityForResult<EditAddressActivity>(1)
        }

        constraint_password.onClick {
            startActivity<EditPasswordActivity>()
        }

        constraint_photo.onClick {
            presenter.checkPhotoExists()
        }
    }
}
