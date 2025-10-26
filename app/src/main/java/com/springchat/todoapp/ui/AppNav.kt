package com.springchat.todoapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.springchat.todoapp.data.repository.InMemoryTasksRepository

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val repo = remember { InMemoryTasksRepository() }
    val factory = remember { TasksListViewModelFactory(repo) }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.TasksList.route
    ) {
        composable(route = Screen.TasksList.route) {
            val viewModel: TasksListViewModel = viewModel(factory = factory)
            val tasks = viewModel.tasks.collectAsStateWithLifecycle().value
            TasksListScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                tasks = tasks,
                onTaskCheckedChange = { taskId, completed ->
                    viewModel.toggleTaskCompletion(taskId, completed)
                },
            )
        }

        composable(route = Screen.TaskDetails.route) {
//            TaskDetailsScreen()
        }

        composable(route = Screen.AddTask.route) {
//            AddTaskScreen()
        }
    }
}


sealed class Screen(val route: String) {
    object TasksList : Screen("tasks_list")
    object TaskDetails : Screen("task_details/{taskId}")
    object AddTask : Screen("add_task")
}