package com.example.routes

import com.example.services.NoteService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.notes() {

    val noteService = NoteService()

    route("/note"){

        get {
            call.respond(noteService.getAllNotes())
        }

        post {
            noteService.insert(call.receive())?.let { note->
                call.respond(note)
            }
        }

        put {
            if (noteService.update(call.receive()))
                call.respond(call.receive())
        }

        delete {
            if (noteService.delete(call.receive()))
                call.respond(call.receive())
        }
    }

}