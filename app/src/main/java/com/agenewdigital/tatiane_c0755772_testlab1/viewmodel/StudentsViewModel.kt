package com.agenewdigital.tatiane_c0755772_testlab1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.agenewdigital.tatiane_c0755772_testlab1.model.Student
import com.agenewdigital.tatiane_c0755772_testlab1.repository.StudentRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class StudentsViewModel(application: Application) :
    AndroidViewModel(application) {
    private val TAG = "StudentsViewModelLog"

    private var studentRepository: StudentRepository = StudentRepository(this.getApplication())
    private var studentDetailsView: LiveData<StudentDetailsView>? = null

    data class StudentDetailsView(
        var id: Long? = null,
        var name: String = "",
        var age: Int = 0,
        var tuition: Double = 0.0,
        var date: Date? = null
    )

    fun addNewStudent(name: String, age: Int, tuition: Double, date: Date) {

        val newStudent = studentRepository.createStudent()
        newStudent.name = name
        newStudent.age = age
        newStudent.tuition = tuition
        newStudent.date = date

        try {
            viewModelScope.launch {
                val newId = studentRepository.addStudent(newStudent)
                Log.i(TAG, "New student $newId added to the database.")
            }
        } catch (e:Exception){
            Log.i("ErrorApp", "Error:" + e)
        }
    }

    fun updateStudent(studentView: StudentDetailsView) {
        GlobalScope.launch {
            studentView.let {
                val student = getStudentClass(it)
                student?.let { studentRepository.updateStudent(student) }
            }
        }
    }

    fun deleteStudent(studentView: StudentDetailsView) {
        GlobalScope.launch {
            studentView.let {
                val student = getStudentClass(it)
                student?.let { studentRepository.deleteStudent(student) }
            }
        }
    }

    fun getAllStudents() = studentRepository.allStudents

    fun getStudent (studentId: Long) : LiveData<StudentDetailsView>? {
        if (studentDetailsView == null) {
            val studentLiveData =  studentRepository.getLiveStudent(studentId)
            studentDetailsView = Transformations.map(studentLiveData) {
                    repositoryStudent ->
                studentView(repositoryStudent)
            }
        }
        return studentDetailsView
    }

    private fun studentView(student: Student): StudentDetailsView {
        return StudentDetailsView(student.id, student.name, student.age, student.tuition, student.date)
    }

    private fun getStudentClass(student: StudentDetailsView): Student {
        return Student(student.id, student.name, student.age, student.tuition, student.date)
    }
}
