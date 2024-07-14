package com.example.pokescouttrainer.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokescouttrainer.R
import com.example.pokescouttrainer.domain.nfc.PokemonNfcData
import com.example.pokescouttrainer.presentation.components.PokemonCard
import com.example.pokescouttrainer.presentation.theme.PokeScoutTrainerTheme
import com.example.pokescouttrainer.presentation.theme.futuraExtraBoldFamily
import com.example.pokescouttrainer.presentation.theme.pokeSansFamily
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Nfc
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    // State TEMP
    private var current = mutableIntStateOf(172) // VERY TEMP

    // View model
    private val viewModel: TrainerViewModel by viewModels()

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

        // Load data
        viewModel.updateNfcData(
            PokemonNfcData(
                speciesId = 110,
                trainerName = "John Duffuger",
                pokemonXp = 42,
            )
        )

        // Visuals
        setContent {
            PokeScoutTrainerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    PokemonCard(state = viewModel.state)
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
                current.intValue = text.drop(3).toInt()
                Log.d("INT", "$current")
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
                            Toast.makeText(this, payload.drop(3), Toast.LENGTH_LONG).show()
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
fun PokemonDisplay(entryNumber: Int) {
    AsyncImage(
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$entryNumber.png",
        placeholder = painterResource(id = R.drawable.onix),
        error = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        filterQuality = FilterQuality.None,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PokemonDisplayPreview() {
    PokemonDisplay(entryNumber = 170)
}

@Composable
fun AppViewComposable(current: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Column {
            TitleBar()
            PokemonDisplay(entryNumber = current)
            StatsBar()
        }
    }
}

@Composable
@Preview
fun AppViewComposablePreview() {
    AppViewComposable(190)
}
