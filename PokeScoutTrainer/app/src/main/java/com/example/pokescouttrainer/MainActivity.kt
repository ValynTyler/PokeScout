package com.example.pokescouttrainer

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.example.pokescouttrainer.ui.theme.PokeScoutTrainerTheme
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import java.security.MessageDigest

class MainActivity : ComponentActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nfc stuff
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE,
        )

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Failed to add MIME type.", e)
            }
        }

        intentFiltersArray = arrayOf(ndef)
        techListsArray = arrayOf(arrayOf(Ndef::class.java.name))

        // Visuals
        setContent {
            PokeScoutTrainerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                val text = readFromTag(it)
            }
        }
    }

    private fun readFromTag(tag: Tag): String {
        val ndef = Ndef.get(tag)
        ndef?.let {
            try {
                it.connect()
                val ndefMessage = it.ndefMessage
                ndefMessage?.let { msg ->
                    for (ndefRecord in msg.records) {
                        if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN &&
                            ndefRecord.type.contentEquals(NdefRecord.RTD_TEXT)) {
                            val payload = String(ndefRecord.payload)
                            Log.d("NFC", "Read content: $payload")
                            Toast.makeText(this, payload, Toast.LENGTH_LONG).show()
                            return payload
                        }
                    }
                }
                it.close()
            } catch (e: Exception) {
                // Log.e("NFC", "Error reading NFC tag", e)
            }
        }
        return "No NFC info"
    }
}

class PointFilterTransformation : Transformation {
    override val cacheKey: String = "PointFilterTransformation"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val paint = Paint().apply {
            isAntiAlias = false
            isFilterBitmap = true
            shader = BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }

        val output = Bitmap.createBitmap(input.width, input.height, input.config)
        val canvas = Canvas(output)
        canvas.drawBitmap(input, 0f, 0f, paint)

        return output
    }
}

@Composable
fun ImageFromUrl(imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .transformations(PointFilterTransformation())
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun AsyncLoadSprite() {
    ImageFromUrl(imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/69.png")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    AsyncLoadSprite()
}
