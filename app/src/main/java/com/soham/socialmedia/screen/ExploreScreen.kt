package com.soham.socialmedia.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.components.PostSingleRow
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.ui.theme.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun ExploreScreen(){
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
            .padding(0.dp, 0.dp, 0.dp, 58.dp)
    ) {
        Text(
            text = "Explore",
            color = Color.White,
            fontSize = 28.sp,
            modifier = Modifier.padding(22.dp)
        )
        val type = SortingChips()
        PostsList(type = type)
    }
}

@Composable
private fun SortingChips(): String {
    var enabled1 by remember{
        mutableStateOf(false)
    }
    var enabled2 by remember{
        mutableStateOf(true)
    }
    var enabled3 by remember{
        mutableStateOf(true)
    }
    var type by remember{
        mutableStateOf("Anything")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(22.dp, 0.dp, 22.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                enabled2 = true
                enabled3 = true
                enabled1 = !enabled1
                type = "Anything"
            },
            enabled = enabled1,
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ChipBackgroundColor,
                contentColor = Color.White,
                disabledBackgroundColor = ChipSelectedColor,
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .wrapContentWidth()
                .height(36.dp)
        ) {
            Text(text = "Anything")
        }
        Button(
            onClick = {
                enabled1 = true
                enabled3 = true
                enabled2 = !enabled2
                type = "Doubts"
                      },
            enabled = enabled2,
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ChipBackgroundColor,
                contentColor = Color.White,
                disabledBackgroundColor = ChipSelectedColor,
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .wrapContentWidth()
                .height(36.dp)
        ) {
            Text(text = "Doubts")
        }
        Button(
            onClick = {
                enabled1 = true
                enabled2 = true
                enabled3 = !enabled3
                type = "Collab"
            },
            enabled = enabled3,
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ChipBackgroundColor,
                contentColor = Color.White,
                disabledBackgroundColor = ChipSelectedColor,
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .wrapContentWidth()
                .height(36.dp)
        ) {
            Text(text = "Collab")
        }
    }
    return type
}

@Composable
private fun PostsList(type:String) {
    val list = FirebaseAuth().getAllPosts(type)
    LazyColumn(
        contentPadding = PaddingValues(22.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(list){post->
            PostSingleRow(post = post, context = LocalContext.current)
        }
    }
}

@Preview
@Composable
fun ExploreScreenPreview(){
    ExploreScreen()
}