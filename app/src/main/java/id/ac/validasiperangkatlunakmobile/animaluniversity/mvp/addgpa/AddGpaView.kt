package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.addgpa

import android.content.Context

interface AddGpaView {
    fun showError(errorCode: Int)
    fun hideError()
    fun getContext() : Context
    fun close(result: Int)
}