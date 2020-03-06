package com.agenewdigital.tatiane_c0755772_testlab1.db

import android.content.Context
import androidx.room.*
import com.agenewdigital.tatiane_c0755772_testlab1.model.Student
import com.agenewdigital.tatiane_c0755772_testlab1.util.DataConverter

@Database(entities = [Student::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class TestLab1Database : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

//        private var instance: TestLab1Database? = null
//
//        fun getInstance(context: Context): TestLab1Database {
//            if (instance == null) {
//
//                instance = Room.databaseBuilder( context.applicationContext,
//                    TestLab1Database::class.java, "TestLab1Android")
//                    .build()
//            }
//
//            return instance as TestLab1Database
//        }

        @Volatile
        private var INSTANCE: TestLab1Database? = null

        fun getInstance(context: Context): TestLab1Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestLab1Database::class.java,
                    "student_database"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}