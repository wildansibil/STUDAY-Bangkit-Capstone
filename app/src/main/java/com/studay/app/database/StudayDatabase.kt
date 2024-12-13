package com.studay.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class StudayDatabase : RoomDatabase() {
    abstract fun userDao(): StudayDao

    companion object {
        @Volatile
        private var INSTANCE: StudayDatabase? = null

        fun getDatabase(context: Context): StudayDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudayDatabase::class.java,
                    "studay_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

