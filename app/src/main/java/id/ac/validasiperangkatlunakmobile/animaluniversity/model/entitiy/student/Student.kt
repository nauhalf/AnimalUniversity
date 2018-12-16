package id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student

data class Student(
        var id: Long? = null,
        var nim: String? = null,
        var password: String? = null,
        var name: String? = null,
        var faculty: String? = null,
        var major: String? = null,
        var image: String? = null,
        var email: String? = null,
        var address: String? = null,
        var targetGpa: Double? = null) {

    companion object {
        const val TABLE_STUDENT: String = "TABLE_STUDENT"
        const val ID: String = "ID_"
        const val NIM: String = "NIM"
        const val PASSWORD: String = "PASSWORD"
        const val NAME: String = "NAME"
        const val FACULTY: String = "FACULTY"
        const val MAJOR: String = "MAJOR"
        const val IMAGE: String = "IMAGE"
        const val EMAIL: String = "EMAIL"
        const val ADDRESS: String = "ADDRESS"
        const val TARGET_GPA: String = "TARGET_GPA"
    }
}