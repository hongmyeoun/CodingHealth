package com.example.codinghealth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// NavHosts
@Composable
fun Main() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NaviRoute.START.name) {
        composable(NaviRoute.START.name) { StartScreen(navController) }
        composable(NaviRoute.HOME.name) { CustomScaffold() }
    }
}

@Composable
private fun Content(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = NaviRoute.HOME.name) {
        composable(NaviRoute.HOME.name) { ContentScreen(innerPadding, NaviRoute.HOME.name) }
        composable(NaviRoute.CART.name) { ContentScreen(innerPadding, NaviRoute.CART.name) }
        composable(NaviRoute.BELL.name) { ContentScreen(innerPadding, NaviRoute.BELL.name) }
        composable(NaviRoute.SEARCH.name) { ContentScreen(innerPadding, NaviRoute.SEARCH.name) }
        composable(NaviRoute.SETTING.name) { ContentScreen(innerPadding, NaviRoute.SETTING.name) }
    }
}


// Screens
@Composable
fun StartScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { navController.navigate(NaviRoute.HOME.name) }) {
            Icon(imageVector = Icons.Default.Home, contentDescription = NaviRoute.HOME.name)
        }
    }
}

@Composable
fun ContentScreen(innerPadding: PaddingValues, text: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
    }
}

// Scaffold UI
@Composable
fun CustomScaffold() {
    val navController = rememberNavController()

    val viewModel = NaviViewModel()
    val text by viewModel.text.collectAsState()

    Scaffold(
        topBar = { TopBar(text) },
        bottomBar = { BottomBar(navController, viewModel) },
        content = { innerPadding -> Content(navController, innerPadding) }
    )
}

@Composable
private fun TopBar(text: String) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text)
        }
        Divider()
    }
}

@Composable
private fun BottomBar(navController: NavHostController, viewModel: NaviViewModel) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButtons(navController, viewModel)
        }
    }
}

// Icon Buttons
@Composable
private fun IconButtons(navController: NavHostController, viewModel: NaviViewModel) {
    IconButton(onClick = { contentIconClicked(navController,viewModel, NaviRoute.HOME.name) }) {
        Icon(imageVector = Icons.Default.Home, contentDescription = NaviRoute.HOME.name, modifier = Modifier.fillMaxSize())
    }
    IconButton(onClick = { contentIconClicked(navController,viewModel, NaviRoute.CART.name) }) {
        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = NaviRoute.CART.name, modifier = Modifier.fillMaxSize())
    }
    IconButton(onClick = { contentIconClicked(navController,viewModel, NaviRoute.BELL.name) }) {
        Icon(imageVector = Icons.Default.Notifications, contentDescription = NaviRoute.BELL.name, modifier = Modifier.fillMaxSize())
    }
    IconButton(onClick = { contentIconClicked(navController,viewModel, NaviRoute.SEARCH.name) }) {
        Icon(imageVector = Icons.Default.Search, contentDescription = NaviRoute.SEARCH.name, modifier = Modifier.fillMaxSize())
    }
    IconButton(onClick = { contentIconClicked(navController,viewModel, NaviRoute.SETTING.name) }) {
        Icon(imageVector = Icons.Default.Settings, contentDescription = NaviRoute.SETTING.name, modifier = Modifier.fillMaxSize())
    }
}

private fun contentIconClicked(navController: NavHostController, viewModel: NaviViewModel, route: String) {
    navController.navigate(route) { popUpTo(navController.graph.id) { inclusive = true } } // true : 이전 화면이 유지되지 않음, false는 기본값
    viewModel.iconClicked(route)
}