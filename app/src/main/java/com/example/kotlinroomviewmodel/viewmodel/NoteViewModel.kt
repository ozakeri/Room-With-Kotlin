package com.example.kotlinroomviewmodel.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.kotlinroomviewmodel.database.DatabaseRepository
import com.example.kotlinroomviewmodel.model.Note

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val repository: DatabaseRepository = DatabaseRepository(application)
    private var allNotes: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}