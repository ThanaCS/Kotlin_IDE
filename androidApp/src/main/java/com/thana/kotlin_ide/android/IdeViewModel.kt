package com.thana.kotlin_ide.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thana.kotlin_ide.IdeRepository
import com.thana.kotlin_ide.IdeResponse
import com.thana.kotlin_ide.IdeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class IdeViewModel : ViewModel() {

    private val _state = MutableStateFlow<IDEState?>(null)
    val state = _state.asStateFlow()

    fun getOutput(script: String) =
        viewModelScope.launch {
            _state.emit(IDEState.OnProgress)
            val response = IdeRepository().getResponse(script)
            when (response.state) {
                IdeResult.State.LOADING -> _state.emit(IDEState.OnProgress)
                IdeResult.State.SUCCESS -> _state.emit(IDEState.OnSuccess(response.data as IdeResponse))
                IdeResult.State.ERROR -> _state.emit(IDEState.OnError(response.error?.message))
                else -> {
                    _state.emit(IDEState.OnProgress)
                }
            }
        }


    sealed class IDEState {
        object OnProgress : IDEState()
        class OnSuccess(val data: IdeResponse) : IDEState()
        class OnError(val error: String?) : IDEState()
    }
}