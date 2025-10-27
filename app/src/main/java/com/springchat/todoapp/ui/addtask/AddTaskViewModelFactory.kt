package com.springchat.todoapp.ui.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.springchat.todoapp.data.repository.TasksRepository

class AddTaskViewModelFactory(private val repo: TasksRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTaskViewModel(repo) as T
        } else {
            throw IllegalArgumentException("Unknown Object")
        }
    }
}