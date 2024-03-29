package com.example.borutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {
    suspend fun convertImageUrlToBitmap(
        imageUrl: String, context: Context
    ): Bitmap? {
        val imageLoader = ImageLoader(context = context)
        val request =
            ImageRequest.Builder(context = context).data(imageUrl).allowHardware(false).build()
        val imageResult = imageLoader.execute(request)
        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColorSwatch(Palette.from(bitmap).generate().vibrantSwatch),
            "darkVibrant" to parseColorSwatch(Palette.from(bitmap).generate().darkVibrantSwatch),
            "onDarkVibrant" to parseBodyColor(
                Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            )
        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parseColor = Integer.toHexString(color.rgb)
            "#$parseColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null) {
            val parseCColor = Integer.toHexString(color)
            "#$parseCColor"
        } else {
            "#FFFFFF"
        }
    }
}