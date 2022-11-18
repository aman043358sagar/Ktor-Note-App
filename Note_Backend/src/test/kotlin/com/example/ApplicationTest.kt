package com.example

import com.example.api.Api
import com.example.model.Note
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.locations.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    val noteApi = Api.createNoteAPI

    @Test // 1. positive scenario for adding a note
    fun `inserting note successfully`(){
        val note = Note(title = "title2", description = "description2", color = 34)
        val response = noteApi.insert(note).execute()
        response.body()?.let {
            println(it)
        }
    }

    @Test
    fun `getting all notes`(){
        val response = noteApi.getAllNotes().execute()
        response.body()?.let {
            println(it)
        }
    }


}