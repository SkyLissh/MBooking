package com.skylissh.mbooking.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.theme.MBookingTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
  val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))

  Scaffold { innerPadding ->
    Box(
      modifier = modifier
        .padding(innerPadding)
        .fillMaxSize()
    ) {
      LottieAnimation(
        lottieComposition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
          .align(Alignment.Center)
          .size(200.dp)
      )
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingScreenPreview() {
  MBookingTheme {
    LoadingScreen()
  }
}
