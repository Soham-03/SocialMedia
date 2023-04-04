package com.soham.socialmedia

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.components.PostSingleRow
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigation() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        MainScreen()
                    }
//                    Column(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        MainScreen()
//                        BottomNavigation()
//                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(){
    var selected by remember{
        mutableStateOf(0)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    )
    {
        Box(
            Modifier.clickable {
                selected = 0
                if(selected==0){
                    Modifier.background(Color.White)
                }
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Navigation1",
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .padding(12.dp)
            )
        }
        Box(
            Modifier.clickable {
                selected = 1
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Navigation1",
                modifier = Modifier
                    .size(24.dp,24.dp)
            )
            if(selected==1){
                Modifier.background(Color.White)
            }
        }
        Box(
            Modifier.clickable {
                selected = 2
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Navigation1",
                modifier = Modifier
                    .size(24.dp,24.dp)
            )
            if(selected==2){
                Modifier.background(Color.White)
            }
        }
        Box(
            Modifier.clickable {
                selected = 3
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Navigation1",
                modifier = Modifier
                    .size(24.dp,24.dp)
            )
            if(selected==3){
                Modifier.background(Color.White)
            }
        }
        Box(
            Modifier.clickable {
                selected = 4
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Navigation1",
                modifier = Modifier
                    .size(24.dp,24.dp)
            )
            if(selected==4){
                Modifier.background(Color.White)
            }
        }
    }
}

@Composable
fun MainScreen(){
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
    ) {
        Text(
            text = "Explore",
            color = Color.White,
            fontSize = 28.sp,
            modifier = Modifier.padding(22.dp)
        )
        SortingChips()
        PostsList(list = listOf(
            Post("GGWP","","asd","",""),
            Post("GGWP","","asd","",""),
            Post("GGWP","","asd","",""),
        ))
    }
}

@Composable
fun SortingChips(){
    var enabled1 by remember{
        mutableStateOf(true)
    }
    var enabled2 by remember{
        mutableStateOf(true)
    }
    var enabled3 by remember{
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(22.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                enabled2 = true
                enabled3 = true
                enabled1 = !enabled1
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
            Text(text = "Latest")
        }
        Button(
            onClick = {
                enabled1 = true
                enabled3 = true
                enabled2 = !enabled2 },
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
            Text(text = "Rating")
        }
        Button(
            onClick = {
                enabled1 = true
                enabled2 = true
                enabled3 = !enabled3
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
            Text(text = "Popularity")
        }
    }
}

@Composable
fun PostsList(list :List<Post>){
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SocialMediaTheme {
        MainScreen()
        BottomNavigation()
    }
}