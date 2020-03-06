package com.agenewdigital.tatiane_c0755772_testlab1.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.agenewdigital.tatiane_c0755772_testlab1.db.StudentDao
import com.agenewdigital.tatiane_c0755772_testlab1.db.TestLab1Database
import com.agenewdigital.tatiane_c0755772_testlab1.model.Student

class StudentRepository(context: Context) {

    private var db = TestLab1Database.getInstance(context)
    private var studentDao: StudentDao = db.studentDao()

//    fun addStudent(student: Student): Long? {
//        val newId = studentDao.insertStudent(student)
//        student.id = newId
//        return newId
//    }

    suspend fun addStudent(student: Student) : Long?  {
        val newId = studentDao.insertStudent(student)
        student.id = newId
        return newId
    }

    fun createStudent() = Student()

    fun getLiveStudent(studentId: Long) = studentDao.loadLiveStudent(studentId)

    fun updateStudent(student: Student) = studentDao.updateStudent(student)

    suspend fun deleteStudent(student: Student) = studentDao.deleteStudent(student)

    val allStudents = studentDao.loadAll()
}