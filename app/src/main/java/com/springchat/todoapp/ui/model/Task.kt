package com.springchat.todoapp.ui.model

data class Task(
    val id: Int,
    val title: String,
    val description: String ? = null,
    val endDate: String,
    val isCompleted: Boolean = false
)
