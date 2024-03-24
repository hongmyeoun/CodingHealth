package com.example.codinghealth.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Query("SELECT * FROM todolist")
    fun getAll(): Flow<List<TodoList>>

    @Insert
    fun insertAll(vararg lists: TodoList)

    @Delete
    fun delete(todoList: TodoList)

    @Update
    fun update(todoList: TodoList)
}