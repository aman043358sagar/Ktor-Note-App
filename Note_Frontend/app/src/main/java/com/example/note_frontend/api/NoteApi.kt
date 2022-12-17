package com.example.note_frontend.api

import com.example.note_frontend.model.Note
import retrofit2.Call
import retrofit2.http.*

interface NoteApi {
    @POST("/note")
    fun insert(@Body note: Note): Call<Note>

    @PUT("/note")
    fun update(@Body note: Note): Call<Note>

    @HTTP(method = "DELETE", path = "/note", hasBody = true)
    fun delete(@Body note: Note): Call<Note>

    @GET("/note")
    fun getAllNotes(): Call<ArrayList<Note>>
}