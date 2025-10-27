package com.springchat.todoapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormFields(
    modifier: Modifier = Modifier,
    title: String,
    titleError: String? = null,
    desc: String? = null,
    endDateTimeEpoch: Long,
    onTitleChange: (String) -> Unit,
    onDescChange: (String) -> Unit,
    onEndDateTime: (Long) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Title")
            },
            maxLines = 1,
            isError = titleError != null,
            supportingText = {
                if (titleError != null) {
                    Text(titleError)
                }
            }
        )

        OutlinedTextField(
            value = desc ?: "",
            onValueChange = onDescChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Description(Optional)")
            },
            maxLines = 5,
            minLines = 3
        )

        Spacer(Modifier.height(16.dp))
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = endDateTimeEpoch
        )

        DatePicker(
            state = datePickerState,
            modifier = Modifier.fillMaxWidth()
        )

        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let {
                onEndDateTime(it)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}