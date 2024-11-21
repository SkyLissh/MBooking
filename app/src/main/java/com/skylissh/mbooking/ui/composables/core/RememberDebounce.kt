package com.skylissh.mbooking.ui.composables.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> rememberDebounce(delayMs: Long = 500L, callback: (T) -> Unit): (T) -> Unit {
  val scope = rememberCoroutineScope()
  var job: Job? = null

  return { param: T ->
    job?.cancel()
    job = scope.launch {
      delay(delayMs)
      callback(param)
    }
  }
}
