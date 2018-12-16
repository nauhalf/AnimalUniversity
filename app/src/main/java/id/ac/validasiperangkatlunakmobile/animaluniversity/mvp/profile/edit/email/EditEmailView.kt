package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.email

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface EditEmailView {
    fun showError(inputCode: Int, errorCode: Int)
    fun hideError(inputCode : Int)
    fun getContext() : Context
    fun close(result: Int)
    fun showLoading()
    fun load(s: Student)
    fun hideLoading()
}