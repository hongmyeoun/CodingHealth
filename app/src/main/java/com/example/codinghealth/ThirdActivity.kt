package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.codinghealth.ui.theme.CodingHealthTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                BaseActivity(
                    currentActivityText = "ThirdActivity",
                    leftButtonText = "SecondActivity",
                    leftPage = SecondActivity::class.java,
                    rightButtonText = "MainActivity",
                    rightPage = MainActivity::class.java
                )
            }
        }
    }
}
