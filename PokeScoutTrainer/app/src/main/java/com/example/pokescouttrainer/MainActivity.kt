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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.example.pokescouttrainer.ui.theme.PokeScoutTrainerTheme

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
                    AppViewComposable(
                        Modifier
                            .fillMaxSize()
                            .then(
                                Modifier.background(
                                    color = Color.Cyan,
                                )
                            ),
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action
        ) {
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
                            ndefRecord.type.contentEquals(NdefRecord.RTD_TEXT)
                        ) {
                            val payload = String(ndefRecord.payload)
                            Log.d("NFC", "Read content: $payload")
                            Toast.makeText(this, payload, Toast.LENGTH_LONG).show()
                            return payload
                        }
                    }
                }
                it.close()
            } catch (e: Exception) {
                Log.e("NFC", "Error reading NFC tag", e)
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
fun ImageFromUrl(imageUrl: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .transformations(PointFilterTransformation())
            .size(Size.ORIGINAL)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

val pokeSansFamily = FontFamily(
    Font(R.font.pokemon_hollow, FontWeight.Light),
    Font(R.font.pokemon_hollow, FontWeight.Normal),
    Font(R.font.pokemon_solid, FontWeight.Medium),
    Font(R.font.pokemon_solid, FontWeight.Bold)
)

@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 55,
    fontSizeDelta: Int = 3,
    offsetX: Int = 0,
    offsetY: Int = 6,
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = (fontSize + fontSizeDelta).sp,
            fontWeight = FontWeight.Bold,
            fontFamily = pokeSansFamily,
            modifier = Modifier
                .fillMaxWidth()
                .offset(offsetX.dp, offsetY.dp)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = pokeSansFamily,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TitleBar() {
    Column(
        modifier = Modifier.background(color = Color.Red)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        HeaderText(text = "PokeCamp", fontSize = 60)
        HeaderText(text = "Trainer", fontSize = 44, modifier = Modifier.offset(0.dp, (-15).dp))
    }
}

@Composable
@Preview
fun TitleBarPreview() {
    TitleBar()
}

@Composable
fun AppViewComposable(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Column {
            TitleBar()
            Image(
                painter = painterResource(id = R.drawable.onix),
                contentDescription = null,
                modifier = modifier
                    .padding(50.dp)
                    .background(color = Color.Yellow),
                contentScale = ContentScale.FillWidth,
            )
//            ImageFromUrl(
//                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/69.png",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(50.dp)
//                    .background(color = Color.Black),
//            )
            Text(
                text = "Hello Jeff",
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
            )
            Text(
                text = "Hello Jeff",
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
            )
            Text(
                text = "Hello Jeff",
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan),
            )
        }
    }
}

@Composable
@Preview
fun AppViewComposablePreview() {
    AppViewComposable(
        modifier = Modifier
            .fillMaxWidth()
    )
}
