package com.soham.socialmedia.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.components.PostSingleRow
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.ui.theme.*

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(0.dp,0.dp,0.dp,58.dp)
            .fillMaxWidth()
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
    ) {
        Text(
            text = "Social Media",
            color = Color.White,
            fontSize = 28.sp,
            modifier = Modifier.padding(22.dp,22.dp,22.dp,0.dp)
        )
        PostsList(
            list = listOf(
                Post("GGWP", "", "asd", "", ""),
                Post("GGWP", "", "asd", "", ""),
                Post("GGWP", "", "asd", "", ""),
            )
        )
    }
}

@Composable
private fun PostsList(list :List<Post>){
    LazyColumn(
        contentPadding = PaddingValues(22.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        items(items = list){post->
            PostSingleRow(post)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}