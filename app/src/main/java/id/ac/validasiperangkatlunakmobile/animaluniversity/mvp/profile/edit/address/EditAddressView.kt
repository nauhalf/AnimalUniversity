package id.ac.validasiperangkatlunakmobile.animaluniversity.mvp.profile.edit.address

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface EditAddressView {
    fun getContext() : Context
    fun close(result: Int)
    fun showLoading()
    fun load(s: Student)
    fun hideLoading()
}