package com.example.codinghealth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    val listCount = mutableStateOf(0)

    fun addList(){
        ++listCount.value
    }

    fun deleteList(){
        --listCount.value
    }
}