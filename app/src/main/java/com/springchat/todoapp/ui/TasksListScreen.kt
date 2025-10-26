package com.springchat.todoapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.springchat.todoapp.ui.model.Task

@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskCheckedChange: (Task, Boolean) -> Unit
) {
    // ? Why surface
    Surface(modifier = modifier) {
        // why does this not work with by?
        // what is by?

        LazyColumn {
            items(
                items = tasks,
                key = { task -> task.id }
            ) { task ->
                TaskItem(
                    modifier = Modifier.fillMaxWidth(),
                    task = task.title,
                    time = task.endDate,
                    checked = task.isCompleted,
                    onCheckedChange = { completed ->
                        onTaskCheckedChange(task, completed)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun TasksListScreenPreview() {
    val tasks = remember {
        mutableListOf(
            Task(
                id = 1,
                title = "Task 1",
                endDate = "2023-01-01",
                isCompleted = false,
            ),
            Task (
                id = 2,
                title = "Task 2",
                endDate = "2023-01-02",
                isCompleted = false,
            ),
            Task (
                id = 3,
                title = "Task 3",
                endDate = "2023-01-03",
                isCompleted = false,
            ),
            Task (
                id = 4,
                title = "Task 4",
                endDate = "2023-01-04",
                isCompleted = false,
            ),
        )
    }
    TasksListScreen(
        modifier = Modifier.fillMaxSize(),
        tasks = tasks,
        onTaskCheckedChange = { _, _ -> }
    )
}


@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: String,
    time: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.5f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
