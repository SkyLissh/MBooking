package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.composables.core.Icon
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Star
import com.skylissh.mbooking.R
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MovieInfoCard(
  title: String,
  releaseDate: LocalDate?,
  runtime: Int,
  voteAverage: Double,
  voteCount: Int,
  modifier: Modifier = Modifier,
) {
  val locale = LocalConfiguration.current.locales.get(0)
  val formattedReleaseDate = remember {
    if (releaseDate == null) return@remember "---"
    DateTimeFormatter.ofPattern("dd.MM.yyyy").format(releaseDate.toJavaLocalDate())
  }
  val formattedRuntime = remember {
    val data = LocalTime(hour = runtime / 60, minute = runtime % 60)
    DateTimeFormatter.ofPattern("HH'h' mm'm'").format(data.toJavaLocalTime())
  }

  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    modifier = modifier
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_2xl)),
      modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = buildAnnotatedString {
            append(formattedRuntime)
            append(" • ")
            append(formattedReleaseDate)
          },
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Normal,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
      ) {
        Text(
          text = stringResource(R.string.reviews),
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Bold,
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xs))
        ) {
          Icon(
            imageVector = Lucide.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(dimensionResource(R.dimen.icon_small))
          )
          Text(
            text = "%.2f".format(locale, voteAverage),
          )
        }
        Text(
          text = "($voteCount)",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieInfoCardPreview() {
  val now = Clock.System.now()

  MovieInfoCard(
    title = "The Dark Knight",
    releaseDate = now.toLocalDateTime(TimeZone.UTC).date,
    runtime = 120,
    voteAverage = 8.2,
    voteCount = 1000
  )
}

