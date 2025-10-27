@file:OptIn(ExperimentalCoroutinesApi::class)

package com.springchat.todoapp

import com.springchat.todoapp.data.repository.FakeTasksRepository
import com.springchat.todoapp.ui.taskslist.TasksListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TasksListViewModelTest {

    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun toggleTaskCompletion_marksAsCompleted() = runTest(testDispatcher){
        // Arrange
        val fakeRepo = FakeTasksRepository()
        val viewModel = TasksListViewModel(fakeRepo)
//        advanceUntilIdle()
        val targetTask = viewModel.tasks.value.first()
        // Act
        viewModel.toggleTaskCompletion(taskId = targetTask.id, completed = true)

        // Assert
        assertEquals(true,  viewModel.tasks.value.first { it.id == targetTask.id }.isCompleted)
    }

    @Test
    fun toggleTaskCompletion_doesntAffectOthers() = runTest(testDispatcher) {
        // Arrange
        val fakeRepo = FakeTasksRepository()
        val viewModel = TasksListViewModel(fakeRepo)
//        advanceUntilIdle()
        val targetTask1 = viewModel.tasks.value.first()
        val targetTask2 = viewModel.tasks.value.last()

        // Act
        viewModel.toggleTaskCompletion(targetTask1.id, true)

        // Assert
        assertEquals(true, viewModel.tasks.value.first { it.id == targetTask1.id}.isCompleted)
        assertEquals(targetTask2.isCompleted, viewModel.tasks.value.first { it.id == targetTask2.id}.isCompleted)
    }

}