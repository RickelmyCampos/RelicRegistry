package com.gilbersoncampos.relicregistry.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gilbersoncampos.relicregistry.data.local.dao.RecordDao
import com.gilbersoncampos.relicregistry.data.local.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}

class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        val list = value.split(",")
        return if (list.size > 1) {
            list.map { it.trim() }
        } else {
            if (list[0].isEmpty()) listOf() else list
        }

    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}