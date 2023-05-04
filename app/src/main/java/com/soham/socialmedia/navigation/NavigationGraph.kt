package com.soham.socialmedia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.screen.LogInScreen
import com.soham.socialmedia.screen.SignUpScreen

@Composable
fun NavigationGraph(
){
    val navController = rememberNavController()
    GlobalConstants.navController = navController
     NavHost(navController = navController, startDestination = Screens.SignUpScreen.route){
         composable(route = Screens.SignUpScreen.route){
            SignUpScreen(navController = navController)
         }
         composable(route = Screens.LogInScreen.route){
             LogInScreen(navController = navController)
         }
         composable(route = Screens.MainScreen.route){
             BottomNavigation()
         }
     }
}