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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.example.pokescouttrainer.ui.theme.PokeScoutTrainerTheme
import org.w3c.dom.Text

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

val futuraExtraBoldFamily = FontFamily(
    Font(R.font.futura_extra_bold, FontWeight.Normal),
)

@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily = pokeSansFamily,
    color: Color = Color.Black
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 55,
    fontSizeDelta: Int = 3,
    fontFamily: FontFamily = pokeSansFamily,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 6.dp,
) {
    Box(
        modifier = modifier
    ) {
        HeaderText(
            text = text,
            fontSize = (fontSize + fontSizeDelta).sp,
            fontFamily = fontFamily,
            modifier = Modifier
                .offset(offsetX, offsetY)
        )
        HeaderText(
            text = text,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            color = Color.White,
        )
    }
}

@Composable
fun TitleBar() {
    Column(
        modifier = Modifier.background(color = Color.Gray)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        TitleText(text = "PokeCamp", fontSize = 60)
        TitleText(
            text = "Trainer",
            fontSize = 35,
            fontSizeDelta = 2,
            offsetY = 4.dp,
            modifier = Modifier.offset(0.dp, (-15).dp)
        )
    }
}

@Composable
@Preview
fun TitleBarPreview() {
    TitleBar()
}

@Composable
fun StatFieldText(text: String, modifier: Modifier = Modifier, color: Color = Color.Gray) {
    Text(
        text = text,
        color = color,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = futuraExtraBoldFamily,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}

@Composable
fun StatField(text: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Gray)
            .padding(start = 15.dp),
    ) {
        StatFieldText(text = text, color = Color.Black, modifier = Modifier.offset(3.dp, 0.dp))
        StatFieldText(text = text, color = Color.White)
    }
}

@Composable
fun StatsBar() {
    Column {
        StatField("Stat 1")
        StatField("Stat 2")
        StatField("Stat 3")
    }
}

@Preview
@Composable
fun StatsBarPreview() {
    StatsBar()
}

@Composable
fun PokemonDisplay(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        placeholder = painterResource(id = R.drawable.onix),
        error = painterResource(id = R.drawable.ic_launcher_background),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PokemonDisplayPreview() {
    PokemonDisplay(imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/168.png")
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
            PokemonDisplay(imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/168.png")
            StatsBar()
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
