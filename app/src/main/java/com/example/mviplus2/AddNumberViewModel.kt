package com.example.mviplus2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel : ViewModel() {

    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState> get() = _viewState

    private var number =0
    init {
        process()
    }
    private fun process() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow()
                .collect { // كدا انا بقوله ان ال channel اللي عندك دي لما تبعتلك داتا اتعامل معاها علي انها flow بعدين اعمل  collect
                    // طيب بص دلوقتي انا محتاج اعرف انهو نية في الintent دي اللي هتيجي
                    when (it) {
                        is MainIntent.AddNumber -> addNumber()
                    }
                }
        }

    }

    //بص دي اكني بعمل reduce
    private fun addNumber() {
        viewModelScope.launch {
            _viewState.value =
                try {
                     MainViewState.Number(number++)
                } catch (e: Exception) {
                    MainViewState.Error(e.message!!)
                }
        }
    }

}
