package com.springchat.todoapp.ui.taskslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.springchat.todoapp.data.repository.TasksRepository
import com.springchat.todoapp.ui.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TasksListViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    val tasks: StateFlow<List<Task>> = tasksRepository.getAllTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun toggleTaskCompletion(taskId: Int, completed: Boolean) {
        tasksRepository.updateTaskCompletion(
            taskId = taskId,
            completed = completed
        )
    }
}