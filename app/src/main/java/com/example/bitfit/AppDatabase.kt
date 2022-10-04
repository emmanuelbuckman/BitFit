package com.example.bitfit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DayEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dayDao() : DayDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            (instance ?: synchronized(this){
                instance?: buildDatabase(context).also { instance = it }
            }) as AppDatabase

        private fun buildDatabase(context: Context) {
            Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "Days-db"
            ).build()
        }
    }
}