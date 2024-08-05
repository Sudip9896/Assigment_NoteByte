package com.raj.notehype.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raj.notehype.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE userEmail = :userEmail")
    fun getNotesByUser(userEmail: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query AND userEmail = :userEmail")
    fun searchNotesByUser(query: String, userEmail: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query")
    fun searchNotes(query: String?): LiveData<List<Note>>
}

