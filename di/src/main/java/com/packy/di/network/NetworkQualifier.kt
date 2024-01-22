package com.packy.di.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Default

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Youtube

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Packy
