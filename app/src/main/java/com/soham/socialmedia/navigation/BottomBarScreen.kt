package com.soham.socialmedia.navigation

import com.soham.socialmedia.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
){
    object Home: BottomBarScreen(
        route = "home",
        title = "home",
        icon = R.drawable.ic_home,
        icon_focused = R.drawable.ic_home_focused
    )

    object Explore: BottomBarScreen(
        route = "explore",
        title = "explore",
        icon = R.drawable.ic_explore,
        icon_focused = R.drawable.ic_explore_focused
    )

    object AddPost: BottomBarScreen(
        route = "add post",
        title = "add post",
        icon = R.drawable.ic_add_post,
        icon_focused = R.drawable.ic_add_post_focused
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "profile",
        icon = R.drawable.ic_profile,
        icon_focused = R.drawable.ic_profile_focused
    )

    object Chat: BottomBarScreen(
        route = "chat",
        title = "chat",
        icon = R.drawable.ic_chat,
        icon_focused = R.drawable.ic_chat_focused
    )

}
