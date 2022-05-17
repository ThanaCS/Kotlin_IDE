package com.thana.kotlin_ide.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thana.kotlin_ide.IdeRepository
import com.thana.kotlin_ide.Response
import com.thana.kotlin_ide.Result
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
                Result.State.LOADING -> _state.emit(IDEState.OnProgress)
                Result.State.SUCCESS -> _state.emit(IDEState.OnSuccess(response.data as Response))
                Result.State.ERROR -> _state.emit(IDEState.OnError(response.error?.message))
                else -> {
                    _state.emit(IDEState.OnProgress)
                }
            }
        }


    sealed class IDEState {
        object OnProgress : IDEState()
        class OnSuccess(val data: Response) : IDEState()
        class OnError(val error: String?) : IDEState()
    }
}