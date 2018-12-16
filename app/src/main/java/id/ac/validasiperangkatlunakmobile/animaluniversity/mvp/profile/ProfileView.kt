package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface ProfileView {
    fun showLoading()
    fun hideLoading()
    fun getContext() : Context
    fun load(s : Student)
    fun reload(result: Int)
    fun showDialog(photo: String?)
    fun snackbar(s: String)
}