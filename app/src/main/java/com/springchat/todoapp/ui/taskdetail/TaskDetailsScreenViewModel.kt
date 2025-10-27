package com.springchat.todoapp.ui.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.springchat.todoapp.data.repository.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailsScreenViewModel(
    private val repo: TasksRepository,
    private val taskId: Int
) : ViewModel() {

    private val _task = repo.getTaskById(taskId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

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

    init {
        viewModelScope.launch {
            _task.collect { task ->
                if (task == null) return@collect
                updateTitle(task.title)
                task.description?.let { updateDesc(it) }
                updateEndDateTime(task.endDateEpoch)
            }
        }
    }

    fun updateTitle(title: String) {
        _title.update { title }
        // activate validation only after save been hit once.
        if (isSaveHitOnce.value) {
            validateTitle()
        }
    }

    fun updateDesc(desc: String) = _description.update { desc }
    fun updateEndDateTime(endDateTime: Long) = _endDateTime.update { endDateTime }

    fun saveTask(): Boolean {
        _isSaveHitOnce.value = true
        if (!validateAll()) {
            return false
        }
        val currentTask = _task.value ?: return false
        repo.updateTask(
            currentTask.copy(
                title = title.value.trim(),
                description = description.value.takeIf { it?.isNotBlank() == true },
                endDateEpoch = endDateTime.value
            )
        )
        return true
    }

    fun deleteTask(): Boolean {
        _task.value?.let {
            repo.deleteTask(it.id)
            return true
        }
        return false
    }

    private fun validateAll(): Boolean {
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