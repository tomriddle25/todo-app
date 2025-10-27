package com.springchat.todoapp.ui

import kotlinx.serialization.Serializable

//sealed class AppScreen(val route: String) {
//    object TasksList : AppScreen("tasks_list")
//    object TaskDetails : AppScreen("task_details/{taskId}")
//    object AddTask : AppScreen("add_task")
//}

@Serializable
object TasksList

@Serializable
data class TaskDetails(val taskId: Int)

@Serializable
object AddTask