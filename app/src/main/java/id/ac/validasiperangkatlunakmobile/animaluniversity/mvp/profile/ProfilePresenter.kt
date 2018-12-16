package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile

import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.ImageSaver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfilePresenter(private val view: ProfileView,
                       private val studentRepo: StudentRepository = StudentRepositoryImpl(view.getContext())) {
    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)

    fun fetchData() {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var student = async { loadStudent() }
            student.await()?.let { view.load(it) }
            view.hideLoading()
        }
    }

    private suspend fun loadStudent(): Student? {
        return pref.getUserLogin()?.let {
            studentRepo.getStudent(it)
        }
    }

    private suspend fun deletePhoto(fileName: String?): Boolean {
        return fileName?.let { ImageSaver(view.getContext()).setFileName(it).deleteFile() }
                ?: run { false }
    }


    fun update(fileName: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val student = async { loadStudent() }
            val delete = async { deletePhoto(student.await()?.image) }
            delete.await()
            val update = GlobalScope.async {
                pref.getUserLogin()?.let {
                    fileName?.let { it1 -> studentRepo.updatePhoto(it, it1) }
                            ?: studentRepo.updatePhoto(it, null)
                }
            }

            update.await()
            val result = suspendCoroutine<Int> { it ->
                val result = 1
                view.getContext().alert("Foto berhasil diperbarui") {
                    also {
                        ctx.setTheme(R.style.AlertDialogTheme)
                    }
                    okButton { it2 ->
                        it2.dismiss()
                        it.resume(result)
                    }
                }.show()
            }
            view.reload(result)
        }
    }

    fun checkPhotoExists() {
        GlobalScope.launch {
            val student = async { loadStudent() }
            view.showDialog(student.await()?.image)
        }
    }

    fun removePhoto(photo: String) {
        GlobalScope.launch {
            val delete = async { deletePhoto(photo) }
            delete.await()
            pref.getUserLogin()?.let {
                studentRepo.updatePhoto(it, null)
            }

            view.snackbar("Foto berhasil dihapus")
        }
    }
}