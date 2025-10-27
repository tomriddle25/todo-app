package com.springchat.todoapp.ui.taskdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.springchat.todoapp.ui.common.TaskFormFields

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    modifier: Modifier = Modifier,
    title: String,
    titleError: String? = null,
    desc: String? = null,
    endDateTimeEpoch: Long,
    onTitleChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onEndDateTime: (Long) -> Unit,
    onSaveTask: () -> Boolean,
    onBackPress: () -> Unit,
    onDelete: () -> Boolean
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Edit Task") },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPress,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onDelete()
                            onBackPress()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Back",
                            )
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val result = onSaveTask()
                    if (result) {
                        onBackPress()
                    }
                },
                modifier = Modifier.size(48.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save"
                    )
                }
            )
        },
        content = { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                TaskFormFields(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    title = title,
                    titleError = titleError,
                    desc = desc,
                    endDateTimeEpoch = endDateTimeEpoch,
                    onTitleChange = onTitleChange,
                    onDescChange = onDescChange,
                    onEndDateTime = onEndDateTime
                )
            }
        }
    )
}