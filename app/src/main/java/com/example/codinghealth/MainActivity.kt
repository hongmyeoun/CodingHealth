package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.codinghealth.model.data.AppDatabase
import com.example.codinghealth.model.TodoListRepository
import com.example.codinghealth.ui.theme.CodingHealthTheme
import com.example.codinghealth.view.TodoList
import com.example.codinghealth.viewModel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // 또는 applicationContext를 사용할 수 있습니다.
        val db = AppDatabase.getDatabase(context)
        val todoListDao = db.todoListDao()
        val repository = TodoListRepository(todoListDao)
        val viewModel = TodoViewModel(repository)

        setContent {
            CodingHealthTheme {
                TodoList(viewModel)
            }
        }
    }
}