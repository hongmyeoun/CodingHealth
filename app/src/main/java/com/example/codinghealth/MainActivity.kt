package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codinghealth.model.LottoModel.genRandomNumbers
import com.example.codinghealth.ui.theme.CodingHealthTheme
import com.example.codinghealth.utill.Utill.calculateBrightness
import com.example.codinghealth.viewModel.LottoViewModel
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

@Composable
fun Main() {
    val viewModel = LottoViewModel()

    // randomNumbers, currentIndex, genNumbers()의 repeat부분 때문에 "번호 생성" 버튼을 눌렀을때
    // 번호 하나만 뜨고 바로 randomNumbers가 초기화 됨
    // 그리고 UI도 번호 하나만 그리고 바로 사라짐
    // 어떻게 해야할지 모르겠습니다.ㅠㅠ
    val randomNumbers by viewModel.randomNumbers.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val showBonusNumber by viewModel.showBonusNumber.collectAsState()
    val randomColorMap by remember { mutableStateOf(mutableMapOf<Int, Color>()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            val maxIndex = if (showBonusNumber) 6 else minOf(currentIndex, randomNumbers.size - 1)

            for (index in 0..maxIndex) {
                val number = randomNumbers[index]
                val isBonusNumber = index == maxIndex && showBonusNumber
                val randomColor = randomColorMap[number] ?: viewModel.getRandomColor()
                randomColorMap[number] = randomColor

                LottoBall(
                    number = number,
                    textColor = viewModel.getTextColor(randomColor),
                    backgroundColor = if (isBonusNumber) Color(0xFFFFEB3B) else randomColor
                )
            }
        }
        Row {
            CustomButton(
                onClick = {
                    viewModel.noShowBonusNumber()
                    viewModel.genNumbers()
                },
                enabled = viewModel.genNumberEnabled(),
                text = "번호 생성"
            )
            CustomButton(
                onClick = { viewModel.resetNumber() },
                enabled = viewModel.resetNumberEnabled(),
                text = "번호 리셋"
            )
        }
        CustomButton(
            onClick = { viewModel.showBonusNumber() },
            enabled = viewModel.checkBonusEnabled(),
            text = "보너스 번호 확인"
        )
    }
}

@Composable
fun LottoBall(
    number: Int,
    textColor: Color,
    backgroundColor: Color
) {
    Text(
        text = number.toString(),
        color = textColor,
        style = TextStyle(fontSize = 20.sp),
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(12.dp)
            .size(48.dp)
            .padding(4.dp)
    )
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String
) {
    Button(
        onClick = { onClick() },
        enabled = enabled
    ) {
        Text(text = text)
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
