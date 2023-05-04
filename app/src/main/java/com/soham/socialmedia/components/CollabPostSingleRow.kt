package com.soham.socialmedia.components

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.ShowInterestedUsers
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.navigation.Screens
import com.soham.socialmedia.ui.theme.CardGradient1
import com.soham.socialmedia.ui.theme.ChipBackgroundColor
import com.soham.socialmedia.ui.theme.ChipSelectedColor
import java.lang.Float

@Composable
fun CollabPostSingleRow(post:Post,context:Context){
    var progress by remember {
        mutableStateOf(0f)
    }
    progress = post.postCollabInterests.toFloat()/40
    println("Interests:"+(post.postCollabInterests.toFloat()/40))
    val size by animateFloatAsState(
        targetValue = progress,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )
    Column(
        modifier = Modifier
            .background(
                color = ChipBackgroundColor
            )
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    )
    {
        Text(
            text = "Your Friend Needs a Collaborator",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp,18.dp)
                .align(Alignment.CenterHorizontally)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 12.dp)
            .height(18.dp)
        )
        {
            Box(modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(CardGradient1)
            )
            Box(modifier = Modifier
                .fillMaxWidth(size)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(ChipSelectedColor)
                .animateContentSize()
            )
        }
        Text(
            text = "${post.postCollabInterests} Interests",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    GlobalConstants.currentSelectedCollab = post
                    val intent = Intent(context,ShowInterestedUsers::class.java)
                    context.startActivity(intent)
                }
        )
        Row(modifier = Modifier
            .padding(0.dp,12.dp)
            .align(Alignment.CenterHorizontally)
        ){
            OutlinedButton(
                onClick = {
                    val updatedInterests = (post.postCollabInterests.toInt()+1).toString()
                    FirebaseAuth().updateShowingInterests(post,updatedInterests)
                    progress = ((post.postCollabInterests.toInt()/40).toFloat())
                },
                border = BorderStroke(1.dp, ChipSelectedColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = CardGradient1,
                    contentColor = ChipSelectedColor
                )
            ) {
                Text(text = "Show Interest")
            }
        }
    }
}

@Preview
@Composable
fun CollabPostSingleRowPreview(){
//    CollabPostSingleRow(Post("","","","","","","","","", emptyList(),"", emptyList()))
}