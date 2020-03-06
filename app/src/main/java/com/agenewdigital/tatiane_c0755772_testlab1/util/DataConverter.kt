package com.agenewdigital.tatiane_c0755772_testlab1.util

import androidx.room.TypeConverter
import java.util.*

class DataConverter {

    @TypeConverter
    fun toDate(dateLong: Long?) : Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?) : Long? {
        return date?.time
    }

}