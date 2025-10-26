package com.springchat.todoapp.data.repository

import com.springchat.todoapp.ui.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasks() : Flow<List<Task>>
    fun updateTaskCompletion(taskId: Int, completed: Boolean)
}

