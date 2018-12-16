package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.targetgpa

import android.app.Activity
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EditTargetGpaPresenter(private val view: EditTargetGpaView,
                             private val repo: StudentRepository = StudentRepositoryImpl(view.getContext())) {
    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)

    fun update(value: Double) {
        GlobalScope.launch(Dispatchers.Main) {
            val targetGpa = GlobalScope.async {
                pref.getUserLogin()?.let { repo.updateTargetGpa(it, value) }
            }
            targetGpa.await()
            val result = suspendCoroutine<Int> { it ->
                val result = Activity.RESULT_OK
                view.getContext().alert("Target IPK berhasil diubah") {
                    also {
                        ctx.setTheme(R.style.AlertDialogTheme)
                    }
                    okButton { it2 ->
                        it2.dismiss()
                        it.resume(result)
                    }
                }.show()
            }
            view.close(result)
        }
    }

    fun validate(s: String): Boolean {
        return if (s.isEmpty()) {
            view.showError(1)
            false
        } else if (s.toDouble() < 0 || s.toDouble() > 4) {
            view.showError(2)
            false
        } else {
            view.hideError()
            true
        }
    }

    fun fetchData() {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var student = GlobalScope.async{
                pref.getUserLogin()?.let {
                    repo.getStudent(it)
                }
            }

            student.await()?.let { view.load(it) }
            view.hideLoading()
        }
    }
}