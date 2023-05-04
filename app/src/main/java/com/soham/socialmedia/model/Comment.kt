package com.soham.socialmedia.model

import androidx.compose.runtime.snapshots.SnapshotStateList

data class Comment(
    val commentId:String,
    val userId:String,
    val username: String,
    val comment:String,
    val replies: SnapshotStateList<Comment>
)
