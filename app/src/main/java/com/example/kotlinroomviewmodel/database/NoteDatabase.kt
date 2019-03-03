package com.example.kotlinroomviewmodel.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.kotlinroomviewmodel.dao.NoteDao
import com.example.kotlinroomviewmodel.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {


    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "notes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}