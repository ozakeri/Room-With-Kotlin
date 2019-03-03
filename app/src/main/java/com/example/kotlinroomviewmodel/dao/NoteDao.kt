package com.example.kotlinroomviewmodel.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.kotlinroomviewmodel.model.Note


@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM note")
    fun deleteAllNotes()
}