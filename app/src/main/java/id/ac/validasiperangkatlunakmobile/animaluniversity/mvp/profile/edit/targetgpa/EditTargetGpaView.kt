package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.targetgpa

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface EditTargetGpaView {
    fun showError(errorCode: Int)
    fun hideError()
    fun getContext() : Context
    fun close(result: Int)
    fun showLoading()
    fun load(s: Student)
    fun hideLoading()
}