package com.agenewdigital.tatiane_c0755772_testlab1.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.agenewdigital.tatiane_c0755772_testlab1.model.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM Student")
    fun loadAll(): LiveData<List<Student>>

    @Query("SELECT * FROM Student WHERE id = :studentId")
    fun loadStudent(studentId: Long): Student
    @Query("SELECT * FROM Student WHERE id = :studentId")
    fun loadLiveStudent(studentId: Long): LiveData<Student>

    @Insert(onConflict = IGNORE)
    suspend fun insertStudent(student: Student): Long

    @Update(onConflict = REPLACE)
    fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)
}
