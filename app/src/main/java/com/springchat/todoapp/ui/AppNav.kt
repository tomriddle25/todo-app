package com.springchat.todoapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.springchat.todoapp.data.repository.InMemoryTasksRepository
import com.springchat.todoapp.ui.addtask.AddTaskScreen
import com.springchat.todoapp.ui.addtask.AddTaskViewModel
import com.springchat.todoapp.ui.addtask.AddTaskViewModelFactory
import com.springchat.todoapp.ui.taskdetail.TaskDetailsScreen
import com.springchat.todoapp.ui.taskdetail.TaskDetailsScreenViewModel
import com.springchat.todoapp.ui.taskdetail.TaskDetailsScreenViewModelFactory
import com.springchat.todoapp.ui.taskslist.TasksListScreen
import com.springchat.todoapp.ui.taskslist.TasksListViewModel
import com.springchat.todoapp.ui.taskslist.TasksListViewModelFactory

@Composable
fun AppNav(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val repo = remember { InMemoryTasksRepository() }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TasksList
    ) {
        composable<TasksList> {
            val factory = remember { TasksListViewModelFactory(repo) }
            val viewModel: TasksListViewModel = viewModel(factory = factory)
            val tasks = viewModel.tasks.collectAsStateWithLifecycle().value
            TasksListScreen(
                modifier = Modifier
                    .fillMaxSize(),
                tasks = tasks,
                onTaskCheckedChange = { taskId, completed ->
                    viewModel.toggleTaskCompletion(taskId, completed)
                },
                onEditTask = { taskId->
                    navController.navigate(TaskDetails(taskId = taskId))
                },
                onAddTask = {
                    navController.navigate(AddTask)
                },
            )
        }

        composable<TaskDetails> { backStackEntry ->
            val taskDetails = backStackEntry.toRoute<TaskDetails>()
            val taskId = taskDetails.taskId
            val factory = remember { TaskDetailsScreenViewModelFactory(repo, taskId) }
            val viewModel: TaskDetailsScreenViewModel = viewModel(factory = factory)
            TaskDetailsScreen(
                modifier = Modifier
                    .fillMaxSize(),
                title = viewModel.title.collectAsStateWithLifecycle().value,
                titleError = viewModel.titleError.collectAsStateWithLifecycle().value,
                desc = viewModel.description.collectAsStateWithLifecycle().value,
                endDateTimeEpoch = viewModel.endDateTime.collectAsStateWithLifecycle().value,
                onTitleChange = viewModel::updateTitle,
                onDescChange = viewModel::updateDesc,
                onEndDateTime = viewModel::updateEndDateTime,
                onSaveTask = viewModel::saveTask,
                onBackPress = {
                    navController.popBackStack()
                },
                onDelete = viewModel::deleteTask
            )
        }

        composable<AddTask> {
            val factory = remember { AddTaskViewModelFactory(repo) }
            val viewModel: AddTaskViewModel = viewModel(factory = factory)
            AddTaskScreen(
                modifier = Modifier
                    .fillMaxSize(),
                title = viewModel.title.collectAsStateWithLifecycle().value,
                titleError = viewModel.titleError.collectAsStateWithLifecycle().value,
                desc = viewModel.description.collectAsStateWithLifecycle().value,
                endDateTimeEpoch = viewModel.endDateTime.collectAsStateWithLifecycle().value,
                onTitleChange = viewModel::updateTitle,
                onDescChange = viewModel::updateDesc,
                onEndDateTime = viewModel::updateEndDateTime,
                onSaveTask = viewModel::saveTask,
                onBackPress = {
                    navController.popBackStack()
                }
            )
        }
    }
}
