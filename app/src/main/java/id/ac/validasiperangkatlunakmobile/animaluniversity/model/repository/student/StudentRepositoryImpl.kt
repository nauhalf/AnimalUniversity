package id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.student

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.database
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.md5
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

class StudentRepositoryImpl(private val context: Context) : StudentRepository {

    override fun getStudent(nim: String): Student? {
        val student : Student? = context.database.use {
            val result = select(Student.TABLE_STUDENT)
                    .whereArgs("(NIM = {n})", "n" to nim)
            result.parseOpt(classParser())
        }

        return student
    }

    override fun getStudent(id: Long): Student? {
        val student : Student? = context.database.use {
            val result = select(Student.TABLE_STUDENT)
                    .whereArgs("(ID_ = {id})", "id" to id)
            result.parseOpt(classParser())
        }
        return student
    }

    override fun updateStudent(student: Student) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.NAME to student.name,
                    Student.TARGET_GPA to student.targetGpa)
                    .whereArgs("(ID_ = {id})", "id" to student.id!!)
                    .exec()
        }
    }

    override fun updatePassword(id: Long, password: String) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.PASSWORD to password.md5())
                    .whereArgs("(ID_ = {id})", "id" to id)
                    .exec()
        }
    }

    override fun updateTargetGpa(id: Long, value: Double) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.TARGET_GPA to value)
                    .whereArgs("(ID_ = {id})", "id" to id)
                    .exec()
        }
    }

    override fun updateEmail(id: Long, email: String) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.EMAIL to email)
                    .whereArgs("(ID_ = {id})", "id" to id)
                    .exec()
        }
    }

    override fun updateAddress(id: Long, address: String?) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.ADDRESS to address)
                    .whereArgs("(ID_ = {id})", "id" to id)
                    .exec()
        }
    }

    override fun updatePhoto(id: Long, fileName: String?) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.IMAGE to fileName)
                    .whereArgs("(ID_ = {id})", "id" to id)
                    .exec()
        }
    }
}