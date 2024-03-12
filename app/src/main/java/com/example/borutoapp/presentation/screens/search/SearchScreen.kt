package com.example.borutoapp.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.presentation.common.ListContent

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery

    val heroes = viewModel.searchedHeroes.collectAsLazyPagingItems()

    Scaffold(topBar = {
        SearchTopBar(text = searchQuery, onTextChange = {
            viewModel.updateSearchQuery(it)
        },
            onSearchClicked = {
                viewModel.searchHeroes(it)
            }) {
            navController.popBackStack()
        }
    }) {
        Column(Modifier.padding(it)) {
            ListContent(heroes = heroes, navController = navController)
        }
    }
}