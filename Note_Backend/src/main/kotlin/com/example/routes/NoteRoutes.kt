package com.example.routes

import com.example.model.Note
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
            val note = call.receive<Note>()
            if (noteService.update(note))
                call.respond(note)
        }

        delete {
            val note = call.receive<Note>()
            if (noteService.delete(note)) {
                call.respond(note)
            }
        }
    }

}