package com.example.codinghealth.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codinghealth.model.data.TodoList
import com.example.codinghealth.model.TodoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoListRepository) : ViewModel() {
    val todoList: StateFlow<List<TodoList>> = repository.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun textUpdate(todoList: TodoList, text: String) {
        val update = todoList.copy(script = text)
        update(update)
    }

    fun checkUpdate(todoList: TodoList, checked: Boolean) {
        val update = todoList.copy(checked = checked)
        update(update)
    }

    fun addList() {
        val newTodoList = TodoList()
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(newTodoList)
        }
    }

    private fun update(todoList: TodoList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todoList)
        }
    }

    fun delete(todoList: TodoList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(todoList)
        }
    }

}

class TodoItemViewModel(initText: String, initChecked: Boolean) : ViewModel() {
    var text by mutableStateOf(initText)
    fun textValueChange(newText: String) {
        text = newText
    }

    var isChecked by mutableStateOf(initChecked)
    fun checkboxValueChange(newBoolean: Boolean){
        isChecked = newBoolean
    }
}