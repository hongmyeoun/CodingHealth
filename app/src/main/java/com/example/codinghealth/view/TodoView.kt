package com.example.codinghealth.view

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.codinghealth.model.data.TodoList
import com.example.codinghealth.viewModel.TodoItemViewModel
import com.example.codinghealth.viewModel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(viewModel: TodoViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) {
        Content(it, viewModel)
    }
}

@Composable
private fun Content(it: PaddingValues, viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {
        items(todoList, key = { item -> item.uid }) {
            val itemViewModel = remember { TodoItemViewModel(it.script, it.checked) }
            CheckList(
                todoList = it,
                viewModel = viewModel,
                itemViewModel = itemViewModel
            )
        }
        item {
            AddButton { viewModel.addList() }
        }
    }
}


@Composable
private fun AddButton(
    onAddClicked: () -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomIconButton(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Rounded.Add,
                onClicked = { onAddClicked() }
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CheckList(
    todoList: TodoList,
    viewModel: TodoViewModel,
    itemViewModel: TodoItemViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = itemViewModel.isChecked,
                onCheckedChange = {
                    itemViewModel.checkboxValueChange(it)
                    viewModel.checkUpdate(todoList, itemViewModel.isChecked)
                }
            )
            TextField(
                modifier = Modifier.weight(3f),
                value = itemViewModel.text,
                onValueChange = { itemViewModel.textValueChange(it) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // TextField 테두리 밑 제거
                    unfocusedIndicatorColor = Color.Transparent, // TextField 테두리 밑 제거
                    disabledContainerColor = Color.Transparent, // 비활성화 색
                    disabledIndicatorColor = Color.Transparent // 비활성화 테두리 밑
                ),
                enabled = !itemViewModel.isChecked,
                textStyle = if (itemViewModel.isChecked) {
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.textUpdate(todoList, itemViewModel.text)
                        focusManager.clearFocus()
                    }
                )
            )
            CustomIconButton(
                modifier = Modifier.weight(0.5f),
                imageVector = Icons.Rounded.Delete,
                onClicked = { viewModel.delete(todoList) }
            )
        }
        Divider()
    }
}