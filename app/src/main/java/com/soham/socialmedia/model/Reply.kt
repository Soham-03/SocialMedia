package com.soham.socialmedia.model

data class Reply(
    val userId:String,
    val replyId:String,
    val reply:String,
    val replies:List<Reply>
)
