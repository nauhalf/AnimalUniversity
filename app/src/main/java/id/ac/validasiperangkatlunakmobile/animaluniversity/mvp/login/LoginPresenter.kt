package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.login

import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginPresenter(private var view: LoginView, private var repo : StudentRepositoryImpl = StudentRepositoryImpl(view.getContext())){
    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)
    fun login(nim: String?, password: String?){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var student = GlobalScope.async{
                val student = nim?.let { repo.getStudent(it) }
                student

            }

            val isSuccess = student.await()?.password?.equals(password?.md5()) ?: false
            view.hideLoading()
            if(!isSuccess){
                view.showLoginFailDialog()
            } else {
                pref.setUserLogin(student.await()?.id)
                view.openMainActivity()
            }
        }
    }

    fun isLoggedIn() {
        if(pref.getUserLogin() != -1L){
            view.openMainActivity()
        }
    }
}