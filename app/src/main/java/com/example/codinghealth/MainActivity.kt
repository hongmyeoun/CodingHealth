package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.codinghealth.ui.theme.CodingHealthTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                ComposeAnimation()
            }
        }
    }
}

@Composable
fun ComposeAnimation(){
    var currentPage by remember { mutableStateOf(0) }
    val pageList = intArrayOf(R.drawable.page0, R.drawable.page1, R.drawable.page2, R.drawable.page3)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Crossfade(targetState = currentPage, label = "") { targetScreen ->
            Image(
                modifier = Modifier.size(256.dp),
                painter = painterResource(id = pageList[targetScreen]),
                contentDescription = null
            )
        }
        Row {
            OutlinedButton(
                onClick = { currentPage = (currentPage - 1 + pageList.size) % pageList.size }
            ) {
                Text(text = "이전")
            }
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedButton(
                onClick = { currentPage = (currentPage + 1) % pageList.size }
            ) {
                Text(text = "다음")
            }
        }
    }
}

class CAModel {
    val pageList = intArrayOf(R.drawable.page0, R.drawable.page1, R.drawable.page2, R.drawable.page3)
}

class CAViewModel : ViewModel(){
    private val caModel = CAModel()

    var currentPage by mutableStateOf(0)
        private set

    fun previousPage(){
        currentPage = (currentPage - 1 + caModel.pageList.size) % caModel.pageList.size
    }

    fun nextImage(){
        currentPage = (currentPage + 1) % caModel.pageList.size
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodingHealthTheme {
        ComposeAnimation()
    }
}