package com.example.borutoapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.borutoapp.util.Constants.BASE_URL
import com.example.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.example.borutoapp.util.PaletteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsScreen(
    navController: NavHostController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val selectedHero = viewModel.selectedHero.collectAsState()

    val colorPalette by viewModel.colorPalette

    if (colorPalette.isNotEmpty())
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero.value,
            colors = colorPalette
        )
    else
        viewModel.generateColorPalette()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                is UiEvents.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        "$BASE_URL${selectedHero.value?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        viewModel.setColorPalette(
                            extractColorsFromBitmap(bitmap)
                        )
                    }
                }
            }
        }
    }
}