package com.example.pokemon.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.imageLoader
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import coil.util.DebugLogger

class MyImageLoader(context: Context) {
    val imageLoader: ImageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .crossfade(true)
        .logger(DebugLogger())
        .build()
}

class CustomPixelTransformation : Transformation {
    override val cacheKey: String = "customPixelTransformation"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val width = input.width
        val height = input.height
        val output = input.copy(input.config, true)

        for (x in 0 until width) {
            for (y in 0 until height) {
                // Example: Invert the color of each pixel
                val pixel = output.getPixel(x, y)
                val red = 255 - Color.red(pixel)
                val green = 255 - Color.green(pixel)
                val blue = 255 - Color.blue(pixel)
                val alpha = Color.alpha(pixel)
                output.setPixel(x, y, Color.argb(alpha, red, green, blue))
            }
        }

        return output
    }
}

@Composable
fun PokemonImage(
    id: Int,
    modifier: Modifier = Modifier,
) {
    val url = urlById(id, true)
    val context = LocalContext.current

    // Load GIF and process frames
    var frames by remember { mutableStateOf<List<ImageBitmap>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(url) {
        val imageLoader = ImageLoader.Builder(context)
            .componentRegistry { add(GifDecoder()) }
            .build()

        val request = ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .build()

        val drawable = imageLoader.execute(request).drawable

        // Process each frame
        val processedFrames = mutableListOf<ImageBitmap>()
        if (drawable is coil.drawable.GifDrawable) {
            for (i in 0 until drawable.frameCount) {
                val frame = drawable.getFrame(i)
                val normalizedBitmap = normalizePixelArt(frame, 96, 96)
                processedFrames.add(normalizedBitmap.asImageBitmap())
            }
        }

        frames = processedFrames
        isLoading = false
    }

    if (isLoading) {
        // Display loading indicator or placeholder
        // For simplicity, you might want to show a progress indicator
        Image(painter = remember { PlaceholderPainter() }, contentDescription = null, modifier = modifier.size(96.dp))
    } else if (frames.isNotEmpty()) {
        // Display animated frames

    }




//    val request = ImageRequest.Builder(LocalContext.current)
//        .transformations(CustomPixelTransformation())
//        .build()
//
//    val imageLoader = MyImageLoader(LocalContext.current).imageLoader
//    imageLoader.enqueue(request = request)
//
//    val painter = rememberImagePainter(
//        request = ImageRequest.Builder(LocalContext.current)
//            .data(urlById(id, true))
//            .size(Size.ORIGINAL) // Load the original size
//            .transformations(object : Transformation {
//                override val cacheKey: String
//                    get() = "normalizePixelArt"
//
//                override suspend fun transform(input: Bitmap, size: Size): Bitmap {
//                    return normalizePixelArt(input, 96, 96).asAndroidBitmap()
//                }
//            })
//            .build()
//    )
//
//
//    Image(
//        painter = painter,
//        contentDescription = null,
//        contentScale = ContentScale.FillWidth,
//        modifier = modifier.fillMaxWidth()
//    )
}

@Composable
fun AnimatedImage(frames: List<ImageBitmap>, modifier: Modifier = Modifier) {
    var currentFrame by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            currentFrame = (currentFrame + 1) % frames.size
            delay(100) // Adjust delay for animation speed
        }
    }

    Image(
        painter = remember { ImageBitmapPainter(frames[currentFrame]) },
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}






fun urlById(speciesId: Int, isGif: Boolean = false): String {
    return if (!isGif) {
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$speciesId.png"
    } else {
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/$speciesId.gif"
    }
}





fun normalizePixelArt(originalBitmap: Bitmap, targetWidth: Int, targetHeight: Int): ImageBitmap {
    val widthDiff = targetWidth - originalBitmap.width
    val heightDiff = targetHeight - originalBitmap.height

    val croppedWidth = if (widthDiff < 0) targetWidth else originalBitmap.width
    val croppedHeight = if (heightDiff < 0) targetHeight else originalBitmap.height

    val newBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(newBitmap)
    val paint = Paint().apply { isFilterBitmap = false }

    // Draw the original bitmap onto the new bitmap with cropping and padding
    canvas.drawColor(Color.TRANSPARENT) // Fill with transparent color
    val srcLeft = if (widthDiff < 0) -widthDiff / 2 else 0
    val srcTop = if (heightDiff < 0) -heightDiff / 2 else 0
    val srcRight = srcLeft + croppedWidth
    val srcBottom = srcTop + croppedHeight

    val dstLeft = if (widthDiff > 0) widthDiff / 2 else 0
    val dstTop = if (heightDiff > 0) heightDiff / 2 else 0
    val dstRight = dstLeft + croppedWidth
    val dstBottom = dstTop + croppedHeight

    canvas.drawBitmap(originalBitmap, android.graphics.Rect(srcLeft, srcTop, srcRight, srcBottom), android.graphics.Rect(dstLeft, dstTop, dstRight, dstBottom), paint)

    return newBitmap.asImageBitmap()
}

@Composable
fun PixelArtImage(bitmap: ImageBitmap, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    Image(
        painter = BitmapPainter(bitmap),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}

//@Preview
//@Composable
//fun PreviewPixelArtImage() {
//    val originalBitmap = ImageBitmap.imageResource(id = R.drawable.) // Replace with your image resource
//    val normalizedBitmap = normalizePixelArt(originalBitmap.asAndroidBitmap(), 96, 96)
//    PixelArtImage(bitmap = normalizedBitmap, modifier = Modifier.size(96.dp))
//}