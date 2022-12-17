package com.example.note_frontend.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.note_frontend.api.Api
import com.example.note_frontend.model.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object NoteRepository {

    private val allNotes = MutableLiveData<ArrayList<Note>>()
    private val noteApi = Api.createNoteAPI

    fun getAllNotes(): MutableLiveData<ArrayList<Note>> {

        GlobalScope.launch {
            val response = noteApi.getAllNotes().execute()
            allNotes.postValue(response.body())
        }

        return allNotes
    }

    fun insert(note: Note){
        GlobalScope.launch {
            val response = noteApi.insert(note).execute()
            response.body()?.let {
                allNotes.value?.add(it)
                allNotes.postValue(allNotes.value)
            }
        }
    }

    fun delete(note: Note){
        GlobalScope.launch {
            val response = noteApi.delete(note).execute()
            Log.d("dsgdsgsdfds", "${response.code()}")
            response.body()?.let {
                Log.d("dsgdsgsdfds", "note: $it")
                allNotes.value?.remove(it)
                allNotes.postValue(allNotes.value)
            }
        }
    }

    fun update(note: Note){
        GlobalScope.launch {
            val response = noteApi.update(note).execute()
            Log.d("dsgdsgsdfds", "code: ${response.code()}")
            Log.d("dsgdsgsdfds", "message: ${response.message()}")
            response.body()?.let {
                Log.d("dsgdsgsdfds", "note: $it")
                allNotes.value?.set(allNotes.value!!.indexOfFirst { it.id == note.id },note)
                allNotes.postValue(allNotes.value)
            }
        }
    }

}