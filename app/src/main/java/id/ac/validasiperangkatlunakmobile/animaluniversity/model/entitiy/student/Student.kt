package id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student

data class Student(
        var id : Long? = null,
        var nim : String? = null,
        var password : String? = null,
        var name : String? = null,
        var targetGpa : Double?  = null) {

    companion object {
        const val TABLE_STUDENT : String = "TABLE_STUDENT"
        const val ID: String = "ID_"
        const val NIM : String = "NIM"
        const val PASSWORD : String = "PASSWORD"
        const val NAME : String = "NAME"
        const val TARGET_GPA : String = "TARGET_GPA"
    }
}