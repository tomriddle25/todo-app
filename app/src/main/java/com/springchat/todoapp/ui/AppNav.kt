package com.springchat.todoapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.TasksList.route
    ) {
        composable(route = Screen.TasksList.route) {
            TasksListScreen(
                tasks = emptyList(),
                onTaskCheckedChange = { _, _ -> },
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
    object TaskDetails : Screen( "task_details/{taskId}")
    object AddTask : Screen("add_task")
}