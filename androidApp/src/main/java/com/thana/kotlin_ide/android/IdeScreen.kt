package com.thana.kotlin_ide.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun IdeScreen(viewModel: IdeViewModel = viewModel()) {
    val output = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(output) {
        viewModel.state.collect {
            output.value = when (it) {
                is IdeViewModel.IDEState.OnError -> {
                    isLoading.value = false
                    it.error.toString()
                }
                is IdeViewModel.IDEState.OnProgress -> {
                    isLoading.value = true
                    "Loading.."
                }
                is IdeViewModel.IDEState.OnSuccess -> {
                    isLoading.value = false
                    it.data.output.toString()
                }
                else -> ""
            }
        }
    }
    Editor(output.value, isLoading.value)
}

