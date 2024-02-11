package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.codinghealth.LottoModel.genRandomNumbers
import com.example.codinghealth.Utill.calculateBrightness
import com.example.codinghealth.ui.theme.CodingHealthTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                Main()
            }
        }
    }
}

// 이건 잘 되는 코드(이렇게 작동하게 하고 싶었음)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodingHealthTheme {
        var randomNumbers by remember { mutableStateOf(listOf<Int>()) }
        var currentIndex by remember { mutableStateOf(0) }
        val coroutineScope = rememberCoroutineScope()
        val randomColorMap by remember { mutableStateOf(mutableMapOf<Int, Color>()) }
        var showBonusNumber by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                randomNumbers.take(if (showBonusNumber) 7 else currentIndex + 1).forEachIndexed { index, number ->
                    val randomColor = if (number == randomNumbers.last() && showBonusNumber) {
                        Color(0xFFFFEB3B) // 보너스 번호의 색상을 고정
                    } else {
                        randomColorMap[number] ?: Color(
                            red = Random.nextFloat(),
                            green = Random.nextFloat(),
                            blue = Random.nextFloat(),
                            alpha = 1f
                        )
                    }
                    randomColorMap[number] = randomColor
                    val textColor = if (calculateBrightness(randomColor) > 0.5f) Color.Black else Color.White

                    LottoBall(
                        number = number,
                        textColor = textColor,
                        backgroundColor = randomColor
                    )
                }
            }
            Row {
                CustomButton(
                    onClick = {
                        showBonusNumber = false
                        coroutineScope.launch {
                            currentIndex = 0
                            randomNumbers = genRandomNumbers()
                            repeat(6) {
                                currentIndex = it
                                delay(1000)
                            }
                        }
                    },
                    enabled = randomNumbers.isEmpty(),
                    text = "번호 뽑기"
                )
                CustomButton(
                    onClick = { randomNumbers = listOf() },
                    enabled = randomNumbers.isNotEmpty(),
                    text = "번호 리셋"
                )
            }
            CustomButton(
                onClick = { showBonusNumber = true },
                enabled = randomNumbers.isNotEmpty() && !showBonusNumber,
                text = "보너스 번호 확인"
            )
        }
    }
}
