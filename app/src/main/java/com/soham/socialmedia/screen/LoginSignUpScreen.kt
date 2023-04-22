package com.soham.socialmedia.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.ui.theme.*
import com.soham.socialmedia.R

@Composable
fun LoginSignUpScreen(){
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
        Text(
            text = "Sign up with one of the following options.",
            color = Color.White,
            fontWeight = FontWeight.Thin,
            modifier = Modifier
                .padding(22.dp,12.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp, 0.dp)
        ){
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .weight(1f)
                    .clickable {
                    }
                ,
                backgroundColor = Color.Transparent,
                shape = RoundedCornerShape(20.dp),
                elevation = 0.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google",
                        alignment  = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(20.dp)
                    )
                }
            }


            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .weight(1f)
                    .clickable {
                    }
                ,
                backgroundColor = Color.Transparent,
                shape = RoundedCornerShape(20.dp),
                elevation = 0.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterVertically)
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen(){
    LoginSignUpScreen()
}