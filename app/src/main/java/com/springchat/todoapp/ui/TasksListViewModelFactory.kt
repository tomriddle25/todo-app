package com.springchat.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.springchat.todoapp.data.repository.TasksRepository

class TasksListViewModelFactory(private val repo: TasksRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksListViewModel(repo) as T
        } else {
            throw IllegalArgumentException("Unknown Model Class")
        }
    }
}