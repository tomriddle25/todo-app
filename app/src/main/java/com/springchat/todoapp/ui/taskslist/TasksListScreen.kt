package com.springchat.todoapp.ui.taskslist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.springchat.todoapp.ui.model.Task
import com.springchat.todoapp.ui.model.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksListScreen(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskCheckedChange: (Int, Boolean) -> Unit,
    onEditTask: (Int) -> Unit,
    onAddTask: () -> Unit,
) {
    // ? Why surface
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("TODO App") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                modifier = Modifier.size(48.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            // why does this not work with by?
            // what is by?
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(
                    items = tasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskItem(
                        modifier = Modifier.fillMaxWidth(),
                        task = task.title,
                        time = task.endDateEpoch.toFormattedDate(),
                        checked = task.isCompleted,
                        onEditButton = {
                            onEditTask(task.id)
                        },
                        onCheckedChange = { completed ->
                            onTaskCheckedChange(task.id, completed)
                        }
                    )
                }
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
    }
    TasksListScreen(
        modifier = Modifier.fillMaxSize(),
        tasks = tasks,
        onTaskCheckedChange = { _, _ -> },
        onAddTask = {},
        onEditTask = {}
    )
}


@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: String,
    time: String,
    checked: Boolean,
    onEditButton: () -> Unit,
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
        IconButton(
            onClick = onEditButton
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
