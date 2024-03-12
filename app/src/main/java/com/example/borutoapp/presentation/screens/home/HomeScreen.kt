package com.example.borutoapp.presentation.screens.home

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.navigation.Screen
import com.example.borutoapp.presentation.common.ListContent
import com.example.borutoapp.ui.theme.statusBarColor

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = viewModel.getAllHeroes.collectAsLazyPagingItems()
    val activity = LocalContext.current as Activity
    val systemBarColor = statusBarColor

    SideEffect {
        activity.window.statusBarColor = systemBarColor.toArgb()
    }

    Scaffold(topBar = {
        HomeTopBar {
            navController.navigate(Screen.Search.route)
        }
    },
        content = {
            Column(Modifier.padding(it)) {
                ListContent(
                    heroes = allHeroes,
                    navController = navController
                )
            }
        })
}

