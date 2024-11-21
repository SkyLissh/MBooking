package com.skylissh.mbooking.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.theme.MBookingTheme

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
  val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_connection))

  Surface(
    color = MaterialTheme.colorScheme.background,
    contentColor = contentColorFor(MaterialTheme.colorScheme.background),
    modifier = modifier.fillMaxSize()
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
      LottieAnimation(
        lottieComposition,
        enableMergePaths = true,
        modifier = Modifier.size(200.dp)
      )
      Text(
        text = stringResource(R.string.something_went_wrong),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )
      Text(
        text = stringResource(R.string.no_connection),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenPreview() {
  MBookingTheme {
    ErrorScreen()
  }
}
