package com.gilbersoncampos.relicregistry.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gilbersoncampos.relicregistry.data.local.dao.RecordDao
import com.gilbersoncampos.relicregistry.data.local.entity.CatalogRecordEntity
import com.gilbersoncampos.relicregistry.data.enums.BodyPosition
import com.gilbersoncampos.relicregistry.data.enums.Condition
import com.gilbersoncampos.relicregistry.data.enums.DecorationLocation
import com.gilbersoncampos.relicregistry.data.enums.Firing
import com.gilbersoncampos.relicregistry.data.enums.GeneralBodyShape
import com.gilbersoncampos.relicregistry.data.enums.Genitalia
import com.gilbersoncampos.relicregistry.data.enums.LowerLimbs
import com.gilbersoncampos.relicregistry.data.enums.ManufacturingMarks
import com.gilbersoncampos.relicregistry.data.enums.ManufacturingTechnique
import com.gilbersoncampos.relicregistry.data.enums.PaintColor
import com.gilbersoncampos.relicregistry.data.enums.PlasticDecoration
import com.gilbersoncampos.relicregistry.data.enums.StatueType
import com.gilbersoncampos.relicregistry.data.enums.SurfaceTreatment
import com.gilbersoncampos.relicregistry.data.enums.Temper
import com.gilbersoncampos.relicregistry.data.enums.UpperLimbs
import com.gilbersoncampos.relicregistry.data.enums.UsageMarks
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [ CatalogRecordEntity::class], version = 1, exportSchema = false)
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
//    @TypeConverter
//    fun fromStringList(value: String): List<String> {
//        val listType = object : TypeToken<List<String>>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromListString(list: List<String>): String {
//        return Gson().toJson(list)
//    }

//    @TypeConverter
//    fun fromEnum(value: Enum<*>): String {
//        return value.name
//    }
//
//    @TypeConverter
//    fun toStatueType(value: String) = StatueType.valueOf(value)
//
//    @TypeConverter
//    fun toCondition(value: String) = Condition.valueOf(value)
//
//    @TypeConverter
//    fun toGeneralBodyShape(value: String) = GeneralBodyShape.valueOf(value)
//
//    @TypeConverter
//    fun toUpperLimbs(value: String) = UpperLimbs.valueOf(value)
//
//    @TypeConverter
//    fun toLowerLimbs(value: String) = LowerLimbs.valueOf(value)
//
//    @TypeConverter
//    fun toGenitalia(value: String) = Genitalia.valueOf(value)
//
//    @TypeConverter
//    fun toFiring(value: String) = Firing.valueOf(value)

   // @TypeConverter
//    fun toTemper(value: String) = Temper.valueOf(value)
//
//    @TypeConverter
//    fun toManufacturingTechnique(value: String) = ManufacturingTechnique.valueOf(value)
//
//    @TypeConverter
//    fun toManufacturingMarks(value: String) = ManufacturingMarks.valueOf(value)
//
//    @TypeConverter
//    fun toUsageMarks(value: String) = UsageMarks.valueOf(value)
//
//    @TypeConverter
//    fun toSurfaceTreatment(value: String) = SurfaceTreatment.valueOf(value)
//
//    @TypeConverter
//    fun toDecorationLocation(value: String) = DecorationLocation.valueOf(value)
//
//    @TypeConverter
//    fun toDecorationType(value: String) = BodyPosition.valueOf(value)
//
//    @TypeConverter
//    fun toPaintColor(value: String) = PaintColor.valueOf(value)
//
//    @TypeConverter
//    fun toPlasticDecoration(value: String) = PlasticDecoration.valueOf(value)
//
//    @TypeConverter
//    fun toBodyPosition(value: String) = BodyPosition.valueOf(value)
}