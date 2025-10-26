package com.springchat.todoapp.data.repository

import com.springchat.todoapp.ui.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTasksRepository: TasksRepository {

    private val _tasks = MutableStateFlow(
        listOf (
            Task(
                id = 1,
                title = "Task 1",
                endDate = "2023-01-01",
                isCompleted = false,
            ),
            Task(
                id = 2,
                title = "Task 2",
                endDate = "2023-01-02",
                isCompleted = false,
            ),
            Task(
                id = 3,
                title = "Task 3",
                endDate = "2023-01-03",
                isCompleted = false,
            ),
            Task(
                id = 4,
                title = "Task 4",
                endDate = "2023-01-04",
                isCompleted = false,
            ),
        )
    )
    override fun getAllTasks(): Flow<List<Task>> {
        return _tasks
    }

    override fun updateTaskCompletion(taskId: Int, completed: Boolean) {
        _tasks.update { tasks ->
            tasks.map {
                if(it.id == taskId) {
                    it.copy(isCompleted = completed)
                } else {
                    it
                }
            }
        }
    }
}