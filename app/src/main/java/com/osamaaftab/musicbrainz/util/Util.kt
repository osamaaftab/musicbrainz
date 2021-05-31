package com.osamaaftab.musicbrainz.util

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class Util {

    companion object : KoinComponent {
        private val context: Context by inject()

        fun bitmapDescriptorFromVector(
            vectorResId: Int
        ): BitmapDescriptor? {
            val vectorDrawable =
                ContextCompat.getDrawable(context, vectorResId)
            vectorDrawable!!.setBounds(
                0,
                0,
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight
            )
            val bitmap = android.graphics.Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }

        fun asset(assetPath: String): String {
            try {
                val inputStream = context.assets.open(assetPath)
                return inputStreamToString(inputStream, "UTF-8")
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        private fun inputStreamToString(inputStream: InputStream, charsetName: String): String {
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, charsetName)
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        }
    }


}