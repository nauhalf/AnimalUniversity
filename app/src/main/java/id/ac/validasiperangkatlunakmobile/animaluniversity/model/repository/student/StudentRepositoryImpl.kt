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
        }
    }

    override fun updatePassword(student: Student) {
        context.database.use {
            update(Student.TABLE_STUDENT,
                    Student.PASSWORD to student.password?.md5())
                    .whereArgs("(ID_ = {id}", "id" to student.id!!)
        }
    }


}