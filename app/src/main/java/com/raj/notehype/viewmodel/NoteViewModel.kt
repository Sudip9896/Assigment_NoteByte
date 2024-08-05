package com.raj.notehype.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.raj.notehype.model.Note
import com.raj.notehype.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepository: NoteRepository) :
    AndroidViewModel(app) {

    fun addNote(note: Note) = viewModelScope.launch {

            noteRepository.insert(note)

    }

    fun deleteNote(note: Note) = viewModelScope.launch {

        noteRepository.delete(note)

    }

    fun updateNote(note: Note) = viewModelScope.launch {

        noteRepository.update(note)

    }

    fun getAllNotes(): LiveData<List<Note>> = noteRepository.getAllNotes()

    fun searchNote(query: String?): LiveData<List<Note>> = noteRepository.searchNotes(query)

    fun getNotesByUser(userEmail: String): LiveData<List<Note>> =
        noteRepository.getNotesByUser(userEmail)

    fun searchNoteByUser(query: String, userEmail: String): LiveData<List<Note>> =
        noteRepository.searchNotesByUser(query, userEmail)
}
