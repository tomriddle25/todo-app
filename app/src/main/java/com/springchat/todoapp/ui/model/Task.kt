package com.springchat.todoapp.ui.model

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Task(
    val id: Int,
    val title: String,
    val description: String ? = null,
    val endDateEpoch: Long,// epoch
    val isCompleted: Boolean = false
)

fun Long.toFormattedDate() : String {
    val dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
}
