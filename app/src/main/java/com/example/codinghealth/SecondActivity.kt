package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.codinghealth.ui.theme.CodingHealthTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                BaseActivity(
                    currentActivityText = "SecondActivity",
                    leftButtonText = "MainActivity",
                    leftPage = MainActivity::class.java,
                    rightButtonText = "ThirdActivity",
                    rightPage = ThirdActivity::class.java
                )
            }
        }
    }
}

// onClick 방식
//BaseActivity(
//    currentActivityText = "SecondActivity",
//    leftButtonText = "MainActivity",
//    rightButtonText = "ThirdActivity",
//    onLeftClicked = { startActivity(Intent(this@SecondActivity, MainActivity::class.java)) },
//    onRightClicked = { startActivity(Intent(this@SecondActivity, ThirdActivity::class.java)) }
//)