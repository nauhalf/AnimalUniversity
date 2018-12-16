package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.password

import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.isValidPassword
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.md5
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EditPasswordPresenter(private val view: EditPasswordView,
                            private val repo: StudentRepository = StudentRepositoryImpl(view.getContext())) {
    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)

    fun update(password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val updatePassword = GlobalScope.async {
                pref.getUserLogin()?.let { repo.updatePassword(it, password) }
            }
            updatePassword.await()
            suspendCoroutine<Int> { it ->
                val result = 2
                view.getContext().alert("Kata sandi berhasil diubah") {
                    also {
                        ctx.setTheme(R.style.AlertDialogTheme)
                    }
                    okButton { it2 ->
                        it2.dismiss()
                        it.resume(result)
                    }
                }.show()
            }
            view.close()
        }
    }

    fun validate(inputCode: Int, s: String): Boolean {
        return if (s.isEmpty()) {
            view.showError(inputCode, 1)
            false
        } else if (inputCode == 1) {
            if (!validatePassword(s)) {
                view.showError(inputCode, 2)
                false
            } else {
                view.hideError(inputCode)
                true
            }
        } else {
            view.hideError(inputCode)
            true
        }
    }

    fun validateNewPassword(new: String?, re: String?): Boolean {
        val a = new?.let { validate(2, it) } ?: run { validate(2, "") }
        val b = re?.let { validate(3, it) } ?: run { validate(3, "") }
        val c = if (!new?.isValidPassword()!!) {
            view.showError(2, 2)
            false
        } else {
            true
        }


        val d = if (!new.equals(re)) {
            view.showError(3, 2)
            false
        } else {
            true
        }

        return a && b && c && d
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
