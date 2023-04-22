package com.soham.socialmedia.screen

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.ui.theme.*
private var currentChip = 1
@Composable
fun AddPostScreen(navController: NavController){
    val context = LocalContext.current
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
        Text(
            text = "Add Post",
            color = Color.White,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(22.dp)
        )
        PostTypeChips()
        var textPostTitle by remember{
            mutableStateOf(TextFieldValue(""))
        }
        var textPostContent by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var errorTitle by remember {
            mutableStateOf(false)
        }
        var errorContent by remember {
            mutableStateOf(false)
        }
        OutlinedTextField(
            value = textPostTitle,
            onValueChange = {
                textPostTitle = it
            },
            singleLine = true,
            placeholder =
            {
                Text(
                    text = "Title goes here..",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Thin
                )
                Spacer(
                    modifier = Modifier
                        .height(18.dp)
                )
            },
            isError = errorTitle,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = ChipSelectedColor,
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        0.04f to CardGradient1,
                        0.52f to CardGradient2,
                        1.0f to CardGradient3,
                        start = Offset.Zero,
                        end = Offset.Infinite,
                        tileMode = TileMode.Clamp
                    )
                ),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 22.sp
            )
        )
        OutlinedTextField(
            value = textPostContent,
            onValueChange = {
                textPostContent = it
            },
            placeholder =
            {
                Text(
                    text = "Content goes here..",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Thin
                )
                Spacer(
                    modifier = Modifier
                        .height(18.dp)
                )
            },
            isError = errorContent,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = ChipSelectedColor,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(22.dp, 0.dp, 22.dp, 22.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        0.04f to CardGradient1,
                        0.52f to CardGradient2,
                        1.0f to CardGradient3,
                        start = Offset.Zero,
                        end = Offset.Infinite,
                        tileMode = TileMode.Clamp
                    )
                ),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 22.sp
            )
        )
        TextButton(
            onClick = {
                if(TextUtils.isEmpty(textPostTitle.text)){
                    errorTitle = true
                }
                else if(TextUtils.isEmpty(textPostContent.text)){
                    errorContent = true
                }
                else{
                    val hashmap = HashMap<String,String>()
                    hashmap["title"] = textPostTitle.text
                    hashmap["content"] = textPostContent.text
                    when (currentChip) {
                        1 -> {
                            hashmap["type"] = "Anything"
                        }
                        2 -> {
                            hashmap["type"] = "Doubts"
                        }
                        else -> {
                            hashmap["type"] = "Collab"
                        }
                    }
                    FirebaseAuth().addPostToDatabase(hashmap = hashmap, context = context, navController = navController)
                }
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp, 10.dp)
                .clip(RoundedCornerShape(10.dp))
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
            Text(text = "Post")
        }
    }
}

@Composable
private fun PostTypeChips(){
    var enabled1 by remember{
        mutableStateOf(false)
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
            .padding(22.dp, 0.dp, 22.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                enabled2 = true
                enabled3 = true
                enabled1 = !enabled1
                currentChip = 1
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
                currentChip = 2
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
            Text(text = "Doubts/errors")
        }
        Button(
            onClick = {
                enabled1 = true
                enabled2 = true
                enabled3 = !enabled3
                currentChip = 3
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
            Text(text = "Colab")
        }
    }
}

@Preview
@Composable
fun AddPostScreenPreview(){
    AddPostScreen(rememberNavController())
}