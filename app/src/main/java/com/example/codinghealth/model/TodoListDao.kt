package com.example.codinghealth.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoListDao {
    @Query("SELECT * FROM todolist")
    fun getAll(): List<TodoList>

    @Delete
    fun delete(todoList: TodoList)

    @Update
    fun update(todoList: TodoList)
}