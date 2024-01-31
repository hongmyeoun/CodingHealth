package com.example.codinghealth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.codinghealth.ui.theme.CodingHealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                BaseActivity(
                    currentActivityText = "MainActivity",
                    leftButtonText = "SecondActivity",
                    leftPage = SecondActivity::class.java,
                    rightButtonText = "ThirdActivity",
                    rightPage = ThirdActivity::class.java
                )
            }
        }
    }
}

@Composable
fun BaseActivity(
    currentActivityText: String,
    leftButtonText: String,
    leftPage: Class<*>,
    rightButtonText: String,
    rightPage: Class<*>
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = currentActivityText)
        MoveActivityButtons(
            leftButtonText = leftButtonText,
            leftPage = leftPage,
            rightButtonText = rightButtonText,
            rightPage = rightPage
        )
    }
}

@Composable
fun MoveActivityButtons(
    leftButtonText: String,
    leftPage: Class<*>,
    rightButtonText: String,
    rightPage: Class<*>
){
    Row {
        PageButton(
            text = leftButtonText,
            activity = leftPage
        )
        PageButton(
            text = rightButtonText,
            activity = rightPage
        )
    }
}

@Composable
fun PageButton(
    text: String,
    activity: Class<*>,
){
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(context, activity)
        context.startActivity(intent)
    }) {
        Text(text = text)
    }
}

// Generic <T:Any>
// 타입을 파라미터화 -> 코드의 재사용성, 안전성 Up

// KClass
// 현재 Activity는 SecondActivity::class.java 이런식으로 .java이고 kotlin, java에서 다 사용가능하다
// KClass는 SecondActivity::class 로 사용할 수 있고, kotlin에서 사용가능하다.

// 사용이유
// 1. 타입 안전성 : 제너릭과 함께 타입을 안전하게 다룸(컴파일러 타입 오류 방지)
// 2. 리플렉션 기능 활용 : KClass를 통해 Class의 메타데이터를 얻어오건 동적으로 클래스 조작하는 기능(리플렉션)활용
// -> 이는 실행중 프로그램의 구조 검사 및 수정에 유용
// 3. 코틀린 DSL에서 사용 : KClass를 통해 코틀린 DSL에서 타입정보를 쉽게 다룸

// 처음에 만든 코드 -> 보통 Class는 Class<*>로 처리한다길래 수정
// 그 전에는 KClass를 사용하고 .java를 붙여줬음 -> Activity Class가 java여서 그냥 Class로 선언
//@Composable
//fun <T:Any, U:Any> MoveActivityButtons(
//    leftButtonText: String,
//    leftPage: Class<T>,
//    rightButtonText: String,
//    rightPage: Class<U>
//){
//    Row {
//        PageButton(
//            text = leftButtonText,
//            activity = leftPage
//        )
//        PageButton(
//            text = rightButtonText,
//            activity = rightPage
//        )
//    }
//}
//
//@Composable
//fun <T:Any> PageButton(
//    text: String,
//    activity: Class<T>,
//){
//    val context = LocalContext.current
//
//    Button(onClick = {
//        val intent = Intent(context, activity)
//        context.startActivity(intent)
//    }) {
//        Text(text = text)
//    }
//}