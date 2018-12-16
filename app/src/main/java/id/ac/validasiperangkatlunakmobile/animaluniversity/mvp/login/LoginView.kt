package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.login

import android.content.Context

interface LoginView {
    fun showLoading()
    fun hideLoading()
    fun showLoginFailDialog()
    fun openMainActivity()
    fun getContext() : Context
}