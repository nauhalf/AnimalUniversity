package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.main

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun getContext() : Context
    fun loadData(s : Student, list : List<Gpa>?)
    fun logout()
    fun openProfileAcitivty()
}