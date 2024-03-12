package com.example.borutoapp.presentation.screens.details

import android.app.Activity
import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ContentAlpha.medium
import coil.compose.rememberAsyncImagePainter
import com.example.borutoapp.R
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.presentation.components.InfoBox
import com.example.borutoapp.presentation.components.OrderedList
import com.example.borutoapp.ui.theme.ICON_SIZE
import com.example.borutoapp.ui.theme.LARGE_PADDING
import com.example.borutoapp.ui.theme.MEDIUM_PADDING
import com.example.borutoapp.ui.theme.MIN_SHEET_HEIGHT
import com.example.borutoapp.ui.theme.SMALL_PADDING
import com.example.borutoapp.ui.theme.titleColor
import com.example.borutoapp.util.Constants.BASE_URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    navController: NavHostController, selectedHero: Hero?, colors: Map<String, String>
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded
        )
    )

    var vibrant by remember {
        mutableStateOf("#000000")
    }

    var darkVibrant by remember {
        mutableStateOf("#000000")
    }

    var onDarkVibrant by remember {
        mutableStateOf("#FFFFFF")
    }

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val activity = LocalContext.current as Activity

    LaunchedEffect(key1 = selectedHero) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
    }

    SideEffect {
        activity.window.statusBarColor = Color(parseColor(darkVibrant)).toArgb()
    }

    BottomSheetScaffold(scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let {
                BottomSheetContent(
                    selectedHero = it,
                    infoBoxIconColor = Color(parseColor(vibrant)),
                    sheetBackgroundColor = Color(parseColor(darkVibrant)),
                    contentColor = Color(parseColor(onDarkVibrant))
                )
            }
        },
        sheetDragHandle = {}) {
        selectedHero?.image?.let { heroImage ->
            BackgroundContent(
                heroImage = heroImage, imageFraction = currentSheetFraction,
                backgroundColor = Color(parseColor(darkVibrant))
            ) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colorScheme.primary,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier
                    .size(ICON_SIZE)
                    .weight(2f),
                tint = contentColor
            )
            Text(
                text = selectedHero.name,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(8f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.bolt),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.power.toString(),
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )

            InfoBox(
                icon = painterResource(id = R.drawable.calendar),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )

            InfoBox(
                icon = painterResource(id = R.drawable.cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.day,
                smallText = "Birthday",
                textColor = contentColor
            )
        }

        Text(
            text = stringResource(R.string.about),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )


        Text(
            text = selectedHero.about,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = contentColor,
            modifier = Modifier
                .alpha(medium)
                .padding(bottom = MEDIUM_PADDING),
            maxLines = 7
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                listOfItems = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                listOfItems = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                listOfItems = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }

}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${heroImage}"
    val painter =
        rememberAsyncImagePainter(imageUrl, error = painterResource(id = R.drawable.placeholder))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight((imageFraction + 0.4f).coerceAtMost(1.0f))
                .align(Alignment.TopStart)
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { onCloseClicked() }, modifier = Modifier.padding(SMALL_PADDING)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White,
                    modifier = Modifier.size(ICON_SIZE)
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {

        val currentValue = bottomSheetState.currentValue
        val targetValue = bottomSheetState.targetValue

        return when {
            currentValue == SheetValue.Hidden && targetValue == SheetValue.Expanded -> 1f
            currentValue == SheetValue.Expanded && targetValue == SheetValue.Expanded -> 0f
            currentValue == SheetValue.Expanded && targetValue == SheetValue.Hidden -> 0f
            else -> 0.6f
        }

    }