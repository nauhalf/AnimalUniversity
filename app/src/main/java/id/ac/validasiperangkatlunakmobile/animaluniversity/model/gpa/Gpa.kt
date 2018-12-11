package id.ac.validasiperangkatlunakmobile.animaluniversity.model.gpa

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gpa(
        var id : Long? = null,
        var id_student : Long? = null,
        var semester : String? = null,
        var value : Double? = null

) : Parcelable {
    companion object {
        const val TABLE_GPA : String = "TABLE_GPA"
        const val ID: String = "ID_"
        const val ID_STUDENT : String = "ID_STUDENT"
        const val SEMESTER : String = "SEMESTER"
        const val VALUE : String = "VALUE"


    }
}