package com.soham.socialmedia.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.soham.socialmedia.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.ui.theme.*

@Composable
fun PostSingleRow(post: Post){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    Brush.linearGradient(
                        0.04f to CardGradient1,
                        0.52f to CardGradient2,
                        1.0f to CardGradient3,
                        start = Offset.Zero,
                        end = Offset.Infinite,
                        tileMode = TileMode.Clamp
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "User's profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 60.dp, height = 60.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Jane Cooper",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            ){
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "Like Button",
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text="6969",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text="6969",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 8.dp, 0.dp, 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "Like Button",
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .align(Alignment.CenterHorizontally)
                            .rotate(180f)
                    )
                }
                Text(
                    text = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(12.dp, 0.dp, 0.dp, 0.dp)
                        .align(Alignment.Top)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Post Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    }
}


@Preview
@Composable
fun preview(){
    PostSingleRow(post = Post("GGWP","","asd","",""))
}