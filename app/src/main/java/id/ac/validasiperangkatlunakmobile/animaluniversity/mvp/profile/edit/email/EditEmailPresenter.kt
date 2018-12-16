package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.email

import android.app.Activity
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.isValidEmail
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.md5
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EditEmailPresenter(private val view: EditEmailView,
                         private val repo: StudentRepository = StudentRepositoryImpl(view.getContext())) {
    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)

    fun update(email: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val updateEmail = GlobalScope.async {
                pref.getUserLogin()?.let { repo.updateEmail(it, email) }
            }
            updateEmail.await()
            val result = suspendCoroutine<Int> { it ->
                val result = Activity.RESULT_OK
                view.getContext().alert("Email berhasil diubah") {
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

    fun validate(inputCode: Int, s: String): Boolean {
        return if (s.isEmpty()) {
            view.showError(inputCode, 1)
            false
        } else if (inputCode == 1) {
            if (!s.isValidEmail()) {
                view.showError(inputCode, 2)
                false
            } else {
                true
            }
        } else if (inputCode == 2) {
            if (!validatePassword(s)) {
                view.showError(inputCode, 2)
                false
            } else {
                true
            }
        } else {
            view.hideError(inputCode)
            true
        }
    }


    fun fetchData() {
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var student = GlobalScope.async {
                pref.getUserLogin()?.let {
                    repo.getStudent(it)
                }
            }

            student.await()?.let { view.load(it) }
            view.hideLoading()
        }
    }

    private fun validatePassword(password: String): Boolean {
        var result = false
        val student = GlobalScope.async {
            pref.getUserLogin()?.let { repo.getStudent(it) }
        }
        runBlocking {
            result = student.await()?.password.equals(password.md5())
        }

        return result
    }

}
