package com.example.codinghealth.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoListDao(): TodoListDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val db = Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = "my.db"
                ).build()
                db
            }
        }
    }
}