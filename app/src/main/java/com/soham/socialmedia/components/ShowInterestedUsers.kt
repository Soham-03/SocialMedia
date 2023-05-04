package com.soham.socialmedia.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.R
import com.soham.socialmedia.model.Comment
import com.soham.socialmedia.model.Post

@Composable
private fun ShowInterestedUsers(){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black.copy(alpha = 0.6f))
    )
    {
        CollabPostSingleRow(post = GlobalConstants.currentSelectedCollab, LocalContext.current)
        LazyColumn(
            modifier = Modifier
                .requiredHeightIn(min = 100.dp,max = 400.dp)
        ){
            items(GlobalConstants.currentSelectedCollab.interestedUsers!!.size){index: Int ->
                val str = GlobalConstants.currentSelectedCollab.interestedUsers!![index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_pfp_placeholder),
                        contentDescription = "User's profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 32.dp, height = 32.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = str,
                        color = Color.White
                    )
                }
            }
        }
    }
}