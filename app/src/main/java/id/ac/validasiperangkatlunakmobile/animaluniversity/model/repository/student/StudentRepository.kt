package id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student

import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface StudentRepository {
    fun getStudent(nim : String) : Student?
    fun updateStudent(student: Student)
    fun updateEmail(id: Long, email: String)
    fun updateAddress(id: Long, address: String?)
    fun updatePassword(id: Long, password: String)
    fun getStudent(id: Long): Student?
    fun updateTargetGpa(id: Long, value: Double)
    fun updatePhoto(id: Long, fileName: String?)
}