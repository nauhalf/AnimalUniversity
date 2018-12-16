package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.gpa

import android.content.Context

interface GpaView {
    fun showError(errorCode: Int)
    fun hideError()
    fun getContext() : Context
    fun close(result: Int)
}