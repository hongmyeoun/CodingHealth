package com.example.codinghealth.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoList(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("checked") val checked: Boolean,
    @ColumnInfo("script") val script: String? = null,
)
