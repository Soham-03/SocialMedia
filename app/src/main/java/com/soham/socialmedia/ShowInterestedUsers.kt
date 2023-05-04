package com.soham.socialmedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.components.CollabPostSingleRow
import com.soham.socialmedia.ui.theme.CardGradient1
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
        CollabPostSingleRow(post = GlobalConstants.currentSelectedCollab, context = LocalContext.current)
        Text(
            text = "Interested users",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(12.dp)
        )
        LazyColumn(
            modifier = Modifier
                .requiredHeightIn(min = 100.dp,max = 400.dp)
        ){
            GlobalConstants.currentSelectedCollab.interestedUsers!!.removeAt(0)
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