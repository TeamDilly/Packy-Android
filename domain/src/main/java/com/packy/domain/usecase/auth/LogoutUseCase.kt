package com.packy.domain.usecase.auth

interface LogoutUseCase {
    suspend fun logout(): Unit
}