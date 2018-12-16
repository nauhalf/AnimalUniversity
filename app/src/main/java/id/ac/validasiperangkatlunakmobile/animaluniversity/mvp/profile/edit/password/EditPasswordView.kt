package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.password

import android.content.Context
interface EditPasswordView {
    fun showError(inputCode: Int, errorCode: Int)
    fun hideError(inputCode : Int)
    fun getContext() : Context
    fun showLoading()
    fun hideLoading()
    fun close()
}