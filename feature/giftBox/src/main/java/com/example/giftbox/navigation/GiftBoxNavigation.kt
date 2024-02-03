package com.example.giftbox.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.giftbox.boxroot.GiftBoxRootScreen
import com.packy.core.animations.asRootComposable

fun NavGraphBuilder.gitBoxNavGraph(
    navController: NavHostController,
    boxId: String
){
    navigation(
        startDestination = GiftBoxRoute.GIFT_BOX_ROOT,
        route = GiftBoxRoute.GIFT_BOX_NAV_GRAPH
    ){
        asRootComposable(
            route = GiftBoxRoute.GIFT_BOX_ROOT
        ){
            GiftBoxRootScreen(
                navController = navController,
                boxId = boxId
            )
        }
    }
}

object GiftBoxRoute{
    const val GIFT_BOX_NAV_GRAPH = "giftBoxNavGraph"
    const val GIFT_BOX_ROOT = "giftBoxRoot"
    const val GIFT_BOX_ERROR = "giftBoxError"
    const val GIFT_BOX_ARR = "giftBoxArr"
    const val GIFT_BOX_MOTION = "giftBoxMotion"
    const val GIFT_BOX_DETAIL_OPEN = "giftBoxDetailOpen"
}