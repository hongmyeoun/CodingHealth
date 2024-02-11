package com.example.codinghealth

import androidx.compose.ui.graphics.Color

object Utill {
    fun calculateBrightness(color: Color): Float {
        // RGB에서 색상의 상대적 밝기 계산
        return (0.299f * color.red + 0.587f * color.green + 0.114f * color.blue)
    }
}