package com.example.note_frontend.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object Api {
    const val BASE_URL = "http://10.0.2.2:7000/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        JacksonConverterFactory.create(
            jacksonObjectMapper()
        )
    ).build()

    val createNoteAPI : NoteApi by lazy {
        retrofit.create(NoteApi::class.java)
    }
}