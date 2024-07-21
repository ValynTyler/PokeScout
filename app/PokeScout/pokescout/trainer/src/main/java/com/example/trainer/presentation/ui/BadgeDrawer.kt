package com.example.trainer.presentation.ui

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.theme.PokeScoutTheme

@Composable
fun BadgeDrawer(
//    state: TrainerState,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet {
//        Text(
//            text = state.nfcData?.trainerGroup?.toString().orEmpty() + " group progress",
//            modifier = Modifier
//                .padding(16.dp),
//        )
//        Divider()
//        Column(
//            verticalArrangement = Arrangement.Top,
//            modifier = modifier
//                .padding(8.dp)
//                .fillMaxSize()
//        ) {
//            for (i in 0..<4) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    modifier = modifier
//                        .fillMaxWidth()
//                ) {
//                    for (j in 0..<3) {
//                        Image(
//                            bitmap = ImageBitmap.imageResource(R.drawable.rainbow_badge),
//                            contentDescription = null,
//                            filterQuality = FilterQuality.None,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .weight(1f),
//                        )
//                    }
//                }
//            }
//        }
    }
}

@Preview
@Composable
fun BadgeDrawerPreview() {
    PokeScoutTheme {
//        BadgeDrawer(TrainerState())
    }
}