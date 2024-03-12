package com.example.borutoapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.wear.compose.material.ContentAlpha.medium
import com.example.borutoapp.ui.theme.SMALL_PADDING

@Composable
fun OrderedList(
    title: String,
    listOfItems: List<String>,
    textColor: Color
) {

    Column {
        Text(
            text = title, color = textColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = SMALL_PADDING)
        )

        listOfItems.forEachIndexed { index, item ->
            Text(
                modifier = Modifier.alpha(medium),
                text = "${index + 1}. $item", color = textColor,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
            )
        }
    }
}