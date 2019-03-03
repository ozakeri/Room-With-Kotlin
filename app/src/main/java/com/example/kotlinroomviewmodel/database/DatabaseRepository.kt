package com.example.kotlinroomviewmodel.database

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.kotlinroomviewmodel.dao.NoteDao
import com.example.kotlinroomviewmodel.model.Note

class DatabaseRepository(application: Application) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

    init {
        val database: NoteDatabase = NoteDatabase.getInstance(application)!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        val insertNote = InsertNote(noteDao).execute(note)
    }

    fun update(note: Note) {
        val updateNote = UpdateNote(noteDao).execute(note)
    }

    fun delete(note: Note) {
        val deleteNote = DeleteNote(noteDao).execute(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }

    fun deleteAllNotes() {
        val deleteAllNote = DeleteAllNotes(noteDao).execute()
    }

    private class InsertNote(noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
        private val noteDao = noteDao
        override fun doInBackground(vararg params: Note?) {
            noteDao.insert(params[0]!!)
        }
    }

    private class UpdateNote(noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
        private val noteDao = noteDao
        override fun doInBackground(vararg params: Note?) {
            noteDao.update(params[0]!!)
        }
    }

    private class DeleteNote(noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
        private val noteDao = noteDao
        override fun doInBackground(vararg params: Note?) {
            noteDao.delete(params[0]!!)
        }
    }

    private class DeleteAllNotes(noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = noteDao
        override fun doInBackground(vararg params: Unit?) {
            noteDao.deleteAllNotes()
        }

    }
}