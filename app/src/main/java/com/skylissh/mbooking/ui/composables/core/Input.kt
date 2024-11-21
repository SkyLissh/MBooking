package com.skylissh.mbooking.ui.composables.core

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.skylissh.mbooking.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  leadingIcon: @Composable (() -> Unit)? = null,
  label: @Composable (() -> Unit)? = null,
  placeholder: @Composable (() -> Unit)? = null,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
  remember { MutableInteractionSource() }

  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    leadingIcon = leadingIcon,
    label = label,
    placeholder = placeholder,
    textStyle = MaterialTheme.typography.bodyLarge,
    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_large)),
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    maxLines = 1,
    colors = OutlinedTextFieldDefaults.colors(
      unfocusedContainerColor = MaterialTheme.colorScheme.surface,
      focusedContainerColor = MaterialTheme.colorScheme.surface,
      disabledContainerColor = MaterialTheme.colorScheme.surface,

      unfocusedBorderColor = Color.Transparent,
      disabledBorderColor = Color.Transparent,
    )
  )

}
