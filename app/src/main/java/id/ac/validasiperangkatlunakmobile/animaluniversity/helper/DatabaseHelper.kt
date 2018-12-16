package id.ac.validasiperangkatlunakmobile.animaluniversity.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.gpa.Gpa
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.entitiy.student.Student
import id.ac.validasiperangkatlunakmobile.animaluniversity.utils.md5
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "AnimalUniversity.db", null, 1) {

    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Student.TABLE_STUDENT, true,
                Student.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Student.NIM to TEXT + UNIQUE,
                Student.PASSWORD to TEXT,
                Student.NAME to TEXT,
                Student.FACULTY to TEXT,
                Student.MAJOR to TEXT,
                Student.IMAGE to TEXT,
                Student.EMAIL to TEXT,
                Student.ADDRESS to TEXT,
                Student.TARGET_GPA to REAL)

        db?.createTable(Gpa.TABLE_GPA, true,
                Gpa.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Gpa.ID_STUDENT to INTEGER,
                Gpa.SEMESTER to INTEGER,
                Gpa.VALUE to REAL,
                Gpa.LOCK to INTEGER)

        seeding(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Student.TABLE_STUDENT, true)
        db?.dropTable(Gpa.TABLE_GPA, true)
    }

    private fun seeding(db: SQLiteDatabase?) {
        val student1 = Student(null,
                "20160801173",
                "20160801173",
                "Muhammmad Naufal Fadhillah",
                "Fakultas Ilmu Komputer",
                "Teknik Informatika",
                null,
                null,
                null,
                null)
        val student2 = Student(null,
                "20160801002",
                "20160801002",
                "Farla Praditha",
                "Fakultas Ilmu Komputer",
                "Teknik Informatika",
                null,
                null,
                null,
                null)
        student1.id = insertStudent(student1, db)
        student2.id = insertStudent(student2, db)

        var listGpaStudent1 = mutableListOf(
                Gpa(null,
                        student1.id,
                        1,
                        3.8,
                        true),
                Gpa(null,
                        student1.id,
                        2,
                        3.5,
                        true),
                Gpa(null,
                        student1.id,
                        3,
                        3.6,
                        true),
                Gpa(null,
                        student1.id,
                        4,
                        3.64,
                        true),
                Gpa(null,
                        student1.id,
                        5,
                        3.3,
                        true))

        var listGpaStudent2 = mutableListOf(
                Gpa(null,
                        student2.id,
                        1,
                        3.4,
                        true),
                Gpa(null,
                        student2.id,
                        2,
                        3.1,
                        true),
                Gpa(null,
                        student2.id,
                        3,
                        3.4,
                        true),
                Gpa(null,
                        student2.id,
                        4,
                        3.3,
                        true),
                Gpa(null,
                        student2.id,
                        5,
                        3.3,
                        true))

        insertGpa(listGpaStudent1, db)
        insertGpa(listGpaStudent2, db)


    }

    private fun insertStudent(student: Student, db: SQLiteDatabase?): Long? {
        return db?.insert(Student.TABLE_STUDENT,
                Student.NIM to student.nim,
                Student.PASSWORD to student.password?.md5(),
                Student.NAME to student.name,
                Student.FACULTY to student.faculty,
                Student.MAJOR to student.major,
                Student.IMAGE to student.image,
                Student.EMAIL to student.email,
                Student.ADDRESS to student.address,
                Student.TARGET_GPA to student.targetGpa)
    }

    private fun insertGpa(list: List<Gpa>, db: SQLiteDatabase?) {
        db?.transaction {
            for (gpa: Gpa in list) {
                db.insert(Gpa.TABLE_GPA,
                        Gpa.ID_STUDENT to gpa.id_student,
                        Gpa.SEMESTER to gpa.semester,
                        Gpa.VALUE to gpa.value,
                        Gpa.LOCK to gpa.lock)
            }
        }
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)