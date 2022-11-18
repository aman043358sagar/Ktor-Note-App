package com.example.api

import com.example.model.Note
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApi {
    @POST("/note")
    fun insert(@Body note: Note): Call<Note>

    @GET("/note")
    fun getAllNotes(): Call<List<Note>>
}