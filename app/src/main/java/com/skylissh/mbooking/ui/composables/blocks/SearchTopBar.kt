package com.skylissh.mbooking.ui.composables.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.composables.core.Icon
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.Lucide
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.composables.core.Input

@Composable
fun SearchTopBar(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier, onGoBack: () -> Unit = {},
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(
        horizontal = dimensionResource(R.dimen.padding_small),
        vertical = dimensionResource(R.dimen.padding_medium)
      )
      .padding(top = dimensionResource(R.dimen.padding_medium))
  ) {
    IconButton(onClick = onGoBack) {
      Icon(
        Lucide.ChevronLeft,
        contentDescription = stringResource(R.string.go_back),
        tint = LocalContentColor.current
      )
    }
    Input(
      value = value,
      onValueChange = onValueChange,
      placeholder = { Text(stringResource(R.string.search)) },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search,
        keyboardType = KeyboardType.Text
      ),
      modifier = Modifier
        .weight(1f)
        .padding(end = dimensionResource(R.dimen.padding_small))
    )
  }
}
