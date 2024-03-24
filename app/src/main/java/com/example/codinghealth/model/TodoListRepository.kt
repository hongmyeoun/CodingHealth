package com.example.codinghealth.model

import com.example.codinghealth.model.data.TodoList
import com.example.codinghealth.model.data.TodoListDao
import kotlinx.coroutines.flow.Flow

class TodoListRepository(private val todoListDao: TodoListDao) {
    fun getAll(): Flow<List<TodoList>>{
        return todoListDao.getAll()
    }

    fun insert(todoList: TodoList){
        todoListDao.insertAll(todoList)
    }

    fun delete(todoList: TodoList){
        todoListDao.delete(todoList)
    }

    fun update(todoList: TodoList){
        todoListDao.update(todoList)
    }

}