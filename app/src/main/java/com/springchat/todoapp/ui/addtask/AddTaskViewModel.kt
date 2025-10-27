package com.springchat.todoapp.ui.addtask

import androidx.lifecycle.ViewModel
import com.springchat.todoapp.data.repository.TasksRepository
import com.springchat.todoapp.ui.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class AddTaskViewModel(private val repo: TasksRepository) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow<String?>(null)
    val description = _description.asStateFlow()

    private val _endDateTime = MutableStateFlow(System.currentTimeMillis())
    val endDateTime = _endDateTime.asStateFlow()

    private val _titleError = MutableStateFlow<String?>(null)
    val titleError = _titleError.asStateFlow()

    private val _isSaveHitOnce = MutableStateFlow(false)
    val isSaveHitOnce = _isSaveHitOnce.asStateFlow()

    fun updateTitle(title: String) {
        _title.update { title }
        // activate validation only after save been hit once.
        if(isSaveHitOnce.value) {
            validateTitle()
        }
    }

    fun updateDesc(desc: String) = _description.update { desc }
    fun updateEndDateTime(endDateTime: Long) = _endDateTime.update { endDateTime }

    fun saveTask(): Boolean {
        _isSaveHitOnce.value = true
        if(!validateAll()) {
            return false
        }
        repo.addTask(
            Task(
                id = Random.nextInt(from = 1, until = Integer.MAX_VALUE),
                title = title.value,
                description = description.value.takeIf { it?.isEmpty() == false },
                endDateEpoch = endDateTime.value,
                isCompleted = false
            )
        )
        return true
    }

    private fun validateAll() : Boolean{
        val valid = validateTitle()
        // Add more later
        return valid
    }

    private fun validateTitle(): Boolean {
        when {
            _title.value.isBlank() -> {
                _titleError.update { "Title can not be blank" }
                return false
            }
            _title.value.length > 100 -> {
                _titleError.update { "Title can not be more than 100 char" }
                return false
            }
            _title.value.length < 3 -> {
                _titleError.update { "Title can not be less than 3 char" }
                return false
            }
            else -> {
                _titleError.update { null }
                return true
            }
         }
    }
}