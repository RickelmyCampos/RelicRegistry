package com.gilbersoncampos.relicregistry.data.local.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gilbersoncampos.relicregistry.Constants.DATE_FORMATER
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
import com.gilbersoncampos.relicregistry.data.local.dao.HistoricSyncDao
import com.gilbersoncampos.relicregistry.data.local.entity.HistoricSyncEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Database(entities = [ CatalogRecordEntity::class, HistoricSyncEntity::class], version = 6, exportSchema = true,autoMigrations = [
    AutoMigration (from = 1, to = 2), AutoMigration(from = 2,to=3),AutoMigration(from =3,to=4),AutoMigration(from=5,to=6)
])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
    abstract fun historicDao(): HistoricSyncDao
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE catalog_records ADD COLUMN createdAt TEXT
        """.trimIndent())

        val now = LocalDateTime.now()
        val dateString=now?.format(DATE_FORMATER)
        database.execSQL("""
            UPDATE catalog_records SET createdAt = '$dateString'
        """.trimIndent())
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE catalog_records ADD COLUMN idRemote TEXT
        """.trimIndent())
    }
}
val MIGRATION_3_4 = object : Migration(3, 4) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE catalog_records ADD COLUMN updatedAt TEXT
        """.trimIndent())

        val now = LocalDateTime.now()
        val dateString=now?.format(DATE_FORMATER)
        database.execSQL("""
            UPDATE catalog_records SET updatedAt = '$dateString'
        """.trimIndent())
    }
}
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // SQL para criar a nova tabela 'historic_sync'
        // Certifique-se de que os tipos de coluna, notNull constraints e chave primária
        // correspondam EXATAMENTE à sua HistoricSyncEntity.
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `historic_sync` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `data` TEXT NOT NULL, 
                `startIn` TEXT NOT NULL, 
                `endIn` TEXT, 
                `status` TEXT NOT NULL, 
                `errorMessage` TEXT
            )
        """.trimIndent())
    }
}
val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE catalog_records ADD COLUMN interiorCondition TEXT
        """.trimIndent())
    }
}
@RequiresApi(Build.VERSION_CODES.O)
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


    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(DATE_FORMATER)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        var nValue=value
        if(value?.isDigitsOnly() == true){
            nValue=Instant.ofEpochMilli(value.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DATE_FORMATER)
        }
        return nValue?.let { LocalDateTime.parse(it, DATE_FORMATER) }
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