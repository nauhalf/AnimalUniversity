package id.ac.validasiperangkatlunakmobile.animaluniversity.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.gpa.Gpa
import id.ac.validasiperangkatlunakmobile.animaluniversity.model.student.Student
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
                Student.TARGET_GPA to REAL)

        db?.createTable(Gpa.TABLE_GPA, true,
                Gpa.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Gpa.ID_STUDENT to INTEGER,
                Gpa.SEMESTER to INTEGER,
                Gpa.VALUE to REAL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Student.TABLE_STUDENT, true)
        db?.dropTable(Gpa.TABLE_GPA, true)
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)