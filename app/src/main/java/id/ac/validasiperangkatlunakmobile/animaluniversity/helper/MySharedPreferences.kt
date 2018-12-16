package id.ac.validasiperangkatlunakmobile.animaluniversity.helper

import android.content.Context
import android.content.SharedPreferences
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.PreferenceHelper.get
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.PreferenceHelper.set
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.AppConstants


object MySharedPreferences : MyPreferencesInterface {
    private lateinit var pref : SharedPreferences

    fun init(context : Context, name : String) : MySharedPreferences{
        pref = PreferenceHelper.customPrefs(context, name)
        return this
    }

    override fun setUserLogin(id: Long?) {
        pref[AppConstants.PREF_USER_LOGIN] = id
    }

    override fun getUserLogin(): Long? {
        return pref[AppConstants.PREF_USER_LOGIN]
    }
}