package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Note(
    val id: Int = -1,
    val title: String,
    val description: String,
    val color: Int
)

object Notes : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val description = varchar("description", 1024)
    val color = integer("color")

    override val primaryKey = PrimaryKey(id)
}