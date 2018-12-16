package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main

import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.MySharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.gpa.GpaRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.gpa.GpaRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepository
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student.StudentRepositoryImpl
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPresenter(private val view: MainView,
                    private val studentRepo : StudentRepository = StudentRepositoryImpl(view.getContext()),
                    private val gpaRepo : GpaRepository = GpaRepositoryImpl(view.getContext())) {
    private val pref = MySharedPreferences.init(view.getContext(), AppConstants.PREF_NAME)

    fun fetchData(){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            var student = GlobalScope.async{
                pref.getUserLogin()?.let {
                    studentRepo.getStudent(it)
                }
            }

            var gpa = GlobalScope.async {
                pref.getUserLogin()?.let {
                    gpaRepo.getGpa(it)
                }
            }

            student.await()?.let { s ->
                gpa.await()?.let {
                    gpa-> view.loadData(s, gpa)
                }
            }
            view.hideLoading()
        }
    }

    fun logout(){
        pref.setUserLogin(null)
        view.logout()
    }
}