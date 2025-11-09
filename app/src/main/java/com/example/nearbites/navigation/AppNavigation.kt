package com.example.nearbites.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nearbites.screens.AuthenticationScreen
import com.example.nearbites.screens.MainScreen
import com.example.nearbites.screens.SplashScreen


@Composable
fun AppNavigation(modifier: Modifier){
    val navController = rememberNavController()
    val startDestination = ScreenName.AuthenticationScreen.name
    PrepareNavGraph(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    )
}

@Composable
fun PrepareNavGraph(modifier: Modifier, navController: NavHostController, startDestination: String) {
     NavHost(
         navController = navController,
         startDestination = startDestination,
         modifier = modifier
     ){
         composable(route = ScreenName.MainScreen.name,
             enterTransition = { defaultEnterTransition() },
             exitTransition = { defaultExitTransition() },
             popEnterTransition = { defaultPopEnterTransition() },
             popExitTransition = { defaultPopExitTransition() }) {
             MainScreen(modifier)
         }
         composable(route = ScreenName.AuthenticationScreen.name,
             enterTransition = { defaultEnterTransition() },
             exitTransition = { defaultExitTransition() },
             popEnterTransition = { defaultPopEnterTransition() },
             popExitTransition = { defaultPopExitTransition() }
             ){
             AuthenticationScreen(modifier,navController)
         }
         composable(route = ScreenName.SplashScreen.name,
             enterTransition = { defaultEnterTransition() },
             exitTransition = { defaultExitTransition() },
             popEnterTransition = { defaultPopEnterTransition() },
             popExitTransition = { defaultPopExitTransition() }
             ){
             SplashScreen(modifier)
         }
     }
}
private fun defaultEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(durationMillis = 0)) +
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300))
}

private fun defaultExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(durationMillis = 0)) +
            slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300))
}

private fun defaultPopEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(durationMillis = 0)) +
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300))
}

private fun defaultPopExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(durationMillis = 0)) +
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
}