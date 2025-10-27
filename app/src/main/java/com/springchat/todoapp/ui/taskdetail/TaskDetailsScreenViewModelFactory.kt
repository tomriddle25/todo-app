package com.springchat.todoapp.ui.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.springchat.todoapp.data.repository.TasksRepository

class TaskDetailsScreenViewModelFactory(
    private val repo: TasksRepository,
    private val taskId: Int
)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskDetailsScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskDetailsScreenViewModel(repo = repo, taskId = taskId) as T
        }
        throw IllegalArgumentException("Unknown Object Used")
    }
}