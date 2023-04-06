package com.soham.socialmedia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.soham.socialmedia.screen.*

@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Explore.route){
            ExploreScreen()
        }
        composable(route = BottomBarScreen.AddPost.route){
            AddPostScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Chat.route){
            ChatScreen()
        }
    }
}