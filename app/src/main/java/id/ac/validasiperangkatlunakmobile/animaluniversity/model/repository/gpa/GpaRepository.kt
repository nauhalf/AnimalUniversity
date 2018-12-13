package id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.gpa

import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
interface GpaRepository {
    fun getGpa(id_student : Long) : List<Gpa>
    fun updateGpa(id : Long, value: Double)
    fun addGpa(gpa : Gpa) : Long
}