package id.ac.validasiperangkatlunakmobile.animaluniversity.model.repository.gpa

import android.content.Context
import id.ac.validasiperangkatlunakmobile.animaluniversity.helper.database
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import org.jetbrains.anko.db.*

class GpaRepositoryImpl (private val context: Context) : GpaRepository {
    override fun getGpa(id_student: Long): List<Gpa> {
        return context.database.use{
            val list  = select(Gpa.TABLE_GPA)
                    .whereArgs(
                            "(ID_STUDENT = {id})",
                            "id" to id_student
                    ).orderBy(Gpa.SEMESTER, SqlOrderDirection.ASC)
                    .parseList(classParser<Gpa>())
            list
        }
    }

    override fun updateGpa(id: Long, value: Double) {
        context.database.use {
            update(Gpa.TABLE_GPA, Gpa.VALUE to value)
                    .whereArgs("ID_ = {id}", "id" to id)
                    .exec()
        }
    }

    override fun addGpa(gpa: Gpa) : Long {
        return context.database.use {
            val id = insert(Gpa.TABLE_GPA,
                    Gpa.ID_STUDENT to gpa.id_student,
                    Gpa.SEMESTER to gpa.semester,
                    Gpa.VALUE to gpa.value,
                    Gpa.LOCK to gpa.lock)
            id
        }

    }
}