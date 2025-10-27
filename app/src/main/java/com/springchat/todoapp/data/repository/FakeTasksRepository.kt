package com.springchat.todoapp.data.repository

import com.springchat.todoapp.ui.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeTasksRepository : TasksRepository {
    val fakeData: MutableStateFlow<List<Task>> = MutableStateFlow(
        listOf(
            Task(
                id = 1,
                title = "Task 1",
                endDateEpoch = System.currentTimeMillis(),
                isCompleted = false,
            ),
            Task(
                id = 2,
                title = "Task 2",
                endDateEpoch = System.currentTimeMillis(),
                isCompleted = false,
            ),
            Task(
                id = 3,
                title = "Task 3",
                endDateEpoch = System.currentTimeMillis(),
                isCompleted = false,
            ),
            Task(
                id = 4,
                title = "Task 4",
                endDateEpoch = System.currentTimeMillis(),
                isCompleted = false,
            ),
        )
    )

    override fun getAllTasks(): Flow<List<Task>> {
        return fakeData
    }

    override fun updateTaskCompletion(taskId: Int, completed: Boolean) {
        fakeData.update { tasks ->
            tasks.map {
                if (it.id == taskId) {
                    it.copy(isCompleted = completed)
                } else {
                    it
                }
            }
        }
    }

    override fun getTaskById(taskId: Int): Flow<Task?> {
        return fakeData.map {
            it.find { it.id == taskId }
        }
    }

    override fun addTask(task: Task) {
        fakeData.update { it + task }
    }

    override fun deleteTask(taskId: Int) {
        fakeData.update { tasks ->
            val targetTask = tasks.firstOrNull { it.id == taskId } ?: return@update tasks
            tasks - targetTask
        }
    }

    override fun updateTask(task: Task) {
        fakeData.update { tasks ->
            tasks.map {
                if (it.id == task.id) {
                    task
                } else {
                    it
                }
            }
        }
    }
}