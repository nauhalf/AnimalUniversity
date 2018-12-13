package id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student

import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student

interface StudentRepository {
    fun getStudent(nim : String) : Student?
    fun updateStudent(student: Student)
    fun updatePassword(student: Student)
    fun getStudent(id: Long): Student?
}