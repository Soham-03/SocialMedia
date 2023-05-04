package com.soham.socialmedia.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.soham.socialmedia.R
import com.soham.socialmedia.components.PostSingleRow
import com.soham.socialmedia.ui.theme.*

@Composable
fun ProfileScreen(){
    val user = FirebaseAuth.getInstance().currentUser
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.linearGradient(
                    0.0f to BackgroundGradient1,
                    0.36f to BackgroundGradient2,
                    0.61f to BackgroundGradient3,
                    0.86f to BackgroundGradient4,
                    1.0f to BackgroundGradient5,
                    start = Offset.Zero,
                    end = Offset.Infinite,
                    tileMode = TileMode.Clamp
                )
            )
            .padding(0.dp, 0.dp, 0.dp, 58.dp)
    )
    {
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 28.sp,
            modifier = Modifier.padding(22.dp,22.dp,22.dp,0.dp)
        )
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(22.dp, 22.dp, 22.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_pfp_placeholder),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(22.dp, 0.dp)
            ) {
                Text(
                    text = user!!.displayName.toString(),
                    fontSize = 24.sp,
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(0.dp, 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Posts",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "69",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Following",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "69",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Followers",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "69",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
        Text(
            text = "Soham Parab",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(22.dp, 0.dp,22.dp,0.dp)
        )
        Text(
            text = "This is Soham Parab, the next big thing?",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(22.dp, 0.dp,22.dp,0.dp)
        )
        PostsGrid()
    }
}

@Composable
fun PostsGrid(){
    val list = com.soham.socialmedia.firebase.FirebaseAuth().getMyPosts()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(22.dp)
            .height(600.dp)
    ){
        items(list){post->
            PostSingleRow(post = post, context = LocalContext.current)
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}