package com.soham.socialmedia.model

data class Post(
    val ownerUid:String,
    val postId:String,
    val content:String,
    val title:String,
    val type:String,
    val likes:String,
    val dislikes:String,
    val likedByMe:String,
    val dislikedByMe:String,
    val postImages: List<String>?,
    val postCollabInterests: String,
    val interestedUsers: MutableList<String>?
)
