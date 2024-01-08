package com.packy.mvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.MviIntentKey
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<Intent : MviIntent, State : UiState, Effect : SideEffect> :
    ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected val intentMap = mutableMapOf<MviIntentKey<out Intent>, ((Intent) -> Any?)>()
    protected val uiIntentMap = mutableMapOf<MviIntentKey<out Intent>, ((State, Intent) -> State)>()

    init {
        this.handleIntent()
        subscribeIntent()
    }

    protected abstract fun handleIntent()

    fun emitIntent(intent: Intent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    fun emitIntent(intent: () -> Intent) {
        emitIntent(intent())
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    protected fun sendEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Intent의 동작을 정의한다.
     */
    @Suppress("UNCHECKED_CAST")
    protected inline fun <reified IT : Intent> subscribeIntent(
        noinline init: (IT) -> Any?
    ) {
        intentMap[MviIntentKey(IT::class.java)] = init as (Intent) -> Any?
    }

    /**
     * State 를 반환 하는 함수를 정의한다.
     * 반환된 State는 uiState 로 emit 된다.
     */
    @Suppress("UNCHECKED_CAST")
    protected inline fun <reified IT : Intent> subscribeStateIntent(
        noinline init: (State, IT) -> State
    ) {
        uiIntentMap[MviIntentKey(IT::class.java)] = init as (State, Intent) -> State
    }

    /**
     * handleIntent 로 등록되 Intent 들의 처리를 수행한다.
     */
    private fun subscribeIntent() {
        viewModelScope.launch {
            intent.collect { intent ->
                intentMap[MviIntentKey(intent::class.java)]
                    ?.let {
                        it(intent)
                    }
                uiIntentMap[MviIntentKey(intent::class.java)]
                    ?.let {
                        val newState = it(currentState, intent)
                        _uiState.emit(newState)
                    }
            }
        }
    }
}