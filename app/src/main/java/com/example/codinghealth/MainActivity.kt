package com.example.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codinghealth.ui.theme.CodingHealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                TodoList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoList() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) {
        Content(it)
    }
}

@Composable
private fun Content(it: PaddingValues) {
    var todoNumber by remember { mutableStateOf(0) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {
        items(todoNumber) {
            CheckList()
        }
        item {
            AddAndDelete(
                onAddClicked = { ++todoNumber },
                onDeleteClicked = { --todoNumber }
            )
        }
    }
}


@Composable
private fun AddAndDelete(
    onAddClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomIconButton(
                modifier = Modifier.weight(0.5f),
                imageVector = Icons.Rounded.Add,
                onClicked = { onAddClicked() }
            )
            CustomIconButton(
                modifier = Modifier.weight(0.5f),
                imageVector = Icons.Rounded.Delete,
                onClicked = { onDeleteClicked() }
            )
        }
        Divider()
    }
}

@Composable
private fun CustomIconButton(
    modifier: Modifier,
    imageVector: ImageVector,
    onClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .clickable { onClicked() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }

}

@Composable
private fun CheckList() {
    var text by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // TextField 테두리 밑 제거
                    unfocusedIndicatorColor = Color.Transparent, // TextField 테두리 밑 제거
                    disabledContainerColor = Color.Transparent, // 비활성화 색
                    disabledIndicatorColor = Color.Transparent // 비활성화 테두리 밑
                ),
                enabled = !isChecked,
                textStyle = if (isChecked) {
                    TextStyle(
                        fontStyle = FontStyle.Italic, // 기울임
                        textDecoration = TextDecoration.LineThrough, // 취소선
                        color = Color.LightGray
                    )
                } else {
                    TextStyle(
                        textDecoration = TextDecoration.None,
                    )
                },
            )
        }
        Divider()
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodingHealthTheme {
        var text by remember { mutableStateOf("") }
        var isChecked by remember { mutableStateOf(false) }
        Column {
            Row {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(
                        textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
            }
        }
    }
}