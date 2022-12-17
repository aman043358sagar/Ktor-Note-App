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
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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
        val stamp = Timestamp(System.currentTimeMillis()) // from java.sql.timestamp
        val date = Date(stamp.time)

        val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        val formattedDateTime = sdf.format(date)

        val note = Note(title = "title2", description = "description2", time = formattedDateTime ,color = 34)
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

    @Test
    fun `deleting note successfully`(){
        val note = Note(9,"Title hello","hello","20 Nov 2022 12:10 AM",2131034160)
        val response = noteApi.delete(note).execute()
        println(response.code())
        response.body()?.let {
            println(it)
        }
    }

    @Test
    fun `updating note successfully`(){
        val note = Note(18,"Title","dog","20 Nov 2022 09:10 PM",2131034156)
        val response = noteApi.update(note).execute()
        println(response.code())
        println(response.message())
        response.body()?.let {
            println(it)
        }
    }

}