package com.soham.socialmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.components.CollabPostSingleRow
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.ui.theme.CardGradient1
import com.soham.socialmedia.ui.theme.ChipBackgroundColor
import com.soham.socialmedia.ui.theme.ChipSelectedColor
import com.soham.socialmedia.ui.theme.SocialMediaTheme
import okhttp3.internal.wait

class ShowInterestedUsers : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(CardGradient1)
    )
    {
        var progress by remember {
            mutableStateOf(0f)
        }
        var buttonText by remember {
            mutableStateOf("Show Interest")
        }
        var enabledStateButton by remember {
            mutableStateOf(true)
        }
//    buttonText = if(interestedButtonState){
//        "Show Interest"
//    }
//    else{
//        "Interested"
//    }
        progress = GlobalConstants.currentSelectedCollab.postCollabInterests.toFloat()/40
//    println("Interests:"+(post.postCollabInterests.toFloat()/40))
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
                    .padding(0.dp, 18.dp)
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
                text = "${GlobalConstants.currentSelectedCollab.postCollabInterests} Interests",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
            )
//            Row(modifier = Modifier
//                .padding(0.dp, 12.dp)
//                .align(Alignment.CenterHorizontally)
//            ){
//                OutlinedButton(
//                    onClick = {
//                        val updatedInterests = (GlobalConstants.currentSelectedCollab.postCollabInterests.toInt()+1).toString()
//                        FirebaseAuth().updateShowingInterests(GlobalConstants.currentSelectedCollab,updatedInterests)
//                        progress = ((GlobalConstants.currentSelectedCollab.postCollabInterests.toInt()/40).toFloat())
//                        buttonText = "Interested"
//                        enabledStateButton = false
//                    },
//                    enabled = enabledStateButton,
//                    border = BorderStroke(1.dp, ChipSelectedColor),
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        backgroundColor = CardGradient1,
//                        contentColor = ChipSelectedColor
//                    )
//                ) {
//                    Text(text = buttonText)
//                }
//            }
        }
        Text(
            text = "Interested users",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(12.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ){
            items(GlobalConstants.currentSelectedCollab.interestedUsers!!.size){index: Int ->
                val str = GlobalConstants.currentSelectedCollab.interestedUsers!![index]
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    SocialMediaTheme {
        Greeting()
    }
}