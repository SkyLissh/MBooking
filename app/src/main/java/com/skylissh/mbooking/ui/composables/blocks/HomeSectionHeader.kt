package com.skylissh.mbooking.ui.composables.blocks

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.composables.core.Icon
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.skylissh.mbooking.R

@Composable
fun HomeSectionHeader(@StringRes title: Int, onClick: () -> Unit) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = dimensionResource(R.dimen.padding_medium))
  ) {
    Text(
      text = stringResource(title),
      style = MaterialTheme.typography.headlineSmall
    )
    TextButton(onClick = onClick) {
      Text(
        text = stringResource(R.string.see_all),
        style = MaterialTheme.typography.bodyMedium
      )
      Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
      Icon(
        imageVector = Lucide.ChevronRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(dimensionResource(R.dimen.icon_small))
      )
    }
  }
}
