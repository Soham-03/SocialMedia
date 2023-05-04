package com.soham.socialmedia.navigation

import com.soham.socialmedia.R

sealed class Screens(
    val route: String
){
    object SignUpScreen: Screens("SignUpScreen",
    )
    object LogInScreen: Screens("LogInScreen",
    )
    object MainScreen: Screens("MainScreen",
    )
    object ShowInterestedUsers: Screens("InterestedUsers"
    )
}