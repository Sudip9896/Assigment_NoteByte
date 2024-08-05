package com.raj.notehype.repository

import com.raj.notehype.database.NoteDatabase
import com.raj.notehype.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insert(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun update(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun delete(note: Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNotes(query: String?) = db.getNoteDao().searchNotes(query)

    fun getNotesByUser(userEmail: String) = db.getNoteDao().getNotesByUser(userEmail)
    fun searchNotesByUser(query: String, userEmail: String) = db.getNoteDao().searchNotesByUser(query, userEmail)
}
