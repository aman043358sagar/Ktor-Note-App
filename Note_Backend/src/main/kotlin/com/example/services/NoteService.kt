package com.example.services

import com.example.model.Note
import com.example.model.Notes
import com.example.utils.DatabaseFactory
import com.example.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class NoteService {

    suspend fun getAllNotes() : List<Note> = dbQuery {
        Notes.selectAll().mapNotNull {
            rowToNote(it)
        }
    }

    suspend fun insert(note: Note): Note? = dbQuery {
        val insertStatement = Notes.insert {
            it[title] = note.title
            it[description] = note.description
            it[time] = note.time
            it[color] = note.color
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::rowToNote)
    }

    suspend fun update(note: Note) : Boolean = dbQuery {
        Notes.update({ Notes.id eq note.id }) {
            it[title] = note.title
            it[description] = note.description
            it[time] = note.time
            it[color] = note.color
        } > 0
    }

    suspend fun delete(note: Note): Boolean = dbQuery {
        Notes.deleteWhere { Notes.id eq note.id } > 0
    }

    private fun rowToNote(row: ResultRow) = Note(
        id = row[Notes.id],
        title = row[Notes.title],
        description = row[Notes.description],
        time = row[Notes.time],
        color = row[Notes.color]
    )
}