package com.soham.socialmedia

import android.net.Uri
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.soham.socialmedia.model.Comment
import com.soham.socialmedia.model.Post
import kotlinx.coroutines.CoroutineScope

object GlobalConstants {
    lateinit var bottomNavController: NavController
    lateinit var coroutineScope: CoroutineScope
    lateinit var interactionSourcee:MutableInteractionSource
    var imagesToUploadLinks = ArrayList<String>()
    var imageToUploadLink: String? = null
    var users = SnapshotStateList<String>()
    var currentSelectedCommentToReply:Comment? = null
    lateinit var currentSelectedCollab:Post
    var navController: NavController? = null
}