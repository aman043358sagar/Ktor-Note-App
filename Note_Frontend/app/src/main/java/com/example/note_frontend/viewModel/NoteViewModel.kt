package com.example.note_frontend.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note_frontend.model.Note
import com.example.note_frontend.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<ArrayList<Note>> = repository.getAllNotes()

    fun insert(note: Note){
        repository.insert(note)
    }

    fun delete(note: Note){
        repository.delete(note)
    }

    fun update(note: Note){
        repository.update(note)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}