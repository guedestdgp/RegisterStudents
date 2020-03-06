package com.agenewdigital.tatiane_c0755772_testlab1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity

data class Student (

    @PrimaryKey(autoGenerate = true) var id: Long? = null,

    var name: String = "",
    var age: Int = 0,
    var tuition: Double = 0.0,
    var date: Date? = null
)