package com.soham.socialmedia.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import com.soham.socialmedia.components.CommentSingleRow
import com.soham.socialmedia.model.Comment
import com.soham.socialmedia.ui.theme.*

@Composable
fun ChatScreen(){
    Column(
        modifier = Modifier
            .fillMaxHeight()
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
    )
    {
//        Text(
//            text = "Coming Soon...",
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Thin,
//            modifier = Modifier
//                .padding(22.dp)
//        )
//        CollabPostSingleRow()
    }
}

@Preview
@Composable
fun ChatScreenPreview(){
    ChatScreen()
}