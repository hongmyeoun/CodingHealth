package com.example.codinghealth

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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