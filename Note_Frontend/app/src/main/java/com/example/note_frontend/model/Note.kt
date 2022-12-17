package com.example.note_frontend.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int = -1,
    val title: String,
    val description: String,
    val time: String,
    val color: Int
) : Parcelable