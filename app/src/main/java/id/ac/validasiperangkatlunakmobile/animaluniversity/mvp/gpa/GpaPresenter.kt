package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.gpa

import android.app.Activity
import id.ac.validasiperangkatlunakmobile.animaluniversity.R
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.gpa.GpaRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.gpa.GpaRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GpaPresenter(private val view: GpaView,
                   private val repo: GpaRepository = GpaRepositoryImpl(view.getContext())) {

    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)
    fun insert(semester: Int, value: Double) {
        GlobalScope.launch(Dispatchers.Main) {
            var gpa = GlobalScope.async {
                pref.getUserLogin()?.let {
                    repo.addGpa(Gpa(null, it, semester, value, false))
                }
            }
            if (gpa.await() != null) {
                val result = suspendCoroutine<Int> { it ->
                    val result = Activity.RESULT_OK
                    view.getContext().alert("Data berhasil ditambahkan") {
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
    }

    fun update(id: Long, value: Double) {
        GlobalScope.launch(Dispatchers.Main) {
            val gpa = GlobalScope.async {
                repo.updateGpa(id, value)
            }
            gpa.await()
            val result = suspendCoroutine<Int> { it ->
                val result = Activity.RESULT_OK
                view.getContext().alert("Data berhasil diubah") {
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
}