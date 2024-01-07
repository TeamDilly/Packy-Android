package com.packy.mvi.mvi

/**
 * MviIntent -> Model -> UiState -> View
 * MviIntent -> SideEffect -> MviIntent -> UiState -> View
 *
 */

/**
 *  View에 보여지는 현재 상태
 */
interface UiState

/**
 *  ViewModel에서 처리해야하는 Intent
 */
interface MviIntent

/**
 *  SideEffect 한번만 발생하는 이슈
 */
interface SideEffect