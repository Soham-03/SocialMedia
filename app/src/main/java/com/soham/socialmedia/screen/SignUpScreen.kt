package com.soham.socialmedia.screen

import android.R.id
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soham.socialmedia.R
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.navigation.Screens
import com.soham.socialmedia.ui.theme.*


@Composable
fun SignUpScreen(
    navController: NavController
){
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
            text = "Sign Up",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(22.dp)
        )
        Text(
            text = "Sign up with one of the following options.",
            color = Color.White,
            modifier = Modifier
                .padding(22.dp,10.dp,22.dp,12.dp)
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
                        .clickable {

                        }
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
                        painter = painterResource(id = R.drawable.ic_apple_logo),
                        contentDescription = "Apple",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(20.dp)
                    )
                }
            }
        }
        EmailPasswordTextFields(context,navController)
        val string = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml("Already have an account? <b><b>LogIn</b></b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        else{
            Html.fromHtml("Already have an account? <b><b>LogIn</b></b>")
        }

        Text(
            text = string.toString(),
            color = Color.White,
            modifier = Modifier
                .padding(22.dp,10.dp,22.dp,12.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate(Screens.LogInScreen.route){
                        launchSingleTop = true
                    }
                }
        )
    }
}

@Composable
private fun EmailPasswordTextFields(context: Context,navController: NavController) {
    var textEmail by remember{
        mutableStateOf(TextFieldValue(""))
    }
    var errorEmail by remember{
        mutableStateOf(false)
    }
    var textPassword by remember{
        mutableStateOf(TextFieldValue(""))
    }
    var errorPassword by remember {
        mutableStateOf(false)
    }
    var textUserName by remember{
        mutableStateOf(TextFieldValue(""))
    }
    var errorUserName by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(0.dp,32.dp)
    )
    {
        Text(
            text = "Username",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(22.dp,22.dp,22.dp,8.dp)
        )
        OutlinedTextField(
            value = textUserName,
            onValueChange = {
                textUserName = it
                errorUserName = TextUtils.isEmpty(textUserName.toString())
            },
            placeholder = {
                Text(text = "Pick a username")
            },
            isError = errorUserName,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = CardGradient1,
                textColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,
                placeholderColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp, 0.dp)
                .clip(RoundedCornerShape(12.dp))
//                .background(
//                    Brush.linearGradient(
//                        0.04f to CardGradient1,
//                        0.52f to CardGradient2,
//                        1.0f to CardGradient3,
//                        start = Offset.Zero,
//                        end = Offset.Infinite,
//                        tileMode = TileMode.Clamp
//                    )
//                )
        )
        Text(
            text = "Email",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(22.dp,22.dp,22.dp,8.dp)
        )
        OutlinedTextField(
            value = textEmail,
            onValueChange = {
                textEmail = it
                errorEmail = TextUtils.isEmpty(textEmail.toString())
            }, 
            placeholder = {
                Text(text = "tim@xyz.com")
            },
            isError = errorEmail,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = CardGradient1,
                textColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,
                placeholderColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp, 0.dp)
                .clip(RoundedCornerShape(12.dp))
//                .background(
//                    Brush.linearGradient(
//                        0.04f to CardGradient1,
//                        0.52f to CardGradient2,
//                        1.0f to CardGradient3,
//                        start = Offset.Zero,
//                        end = Offset.Infinite,
//                        tileMode = TileMode.Clamp
//                    )
//                )
        )
        Text(
            text = "Password",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(22.dp,22.dp,22.dp,8.dp)
        )
        OutlinedTextField(
            value = textPassword,
            onValueChange = {
                textPassword = it
                errorPassword = TextUtils.isEmpty(textPassword.toString())
            },
            placeholder = {
                Text(
                    text = "Pick a String password"
                )
            },
            isError = errorPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = CardGradient1,
                textColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                unfocusedLabelColor = Color.White,
                focusedLabelColor = Color.White,
                placeholderColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp, 0.dp)
                .clip(RoundedCornerShape(12.dp))
//                .background(
//                    Brush.linearGradient(
//                        0.04f to CardGradient1,
//                        0.52f to CardGradient2,
//                        1.0f to CardGradient3,
//                        start = Offset.Zero,
//                        end = Offset.Infinite,
//                        tileMode = TileMode.Clamp
//                    )
//                )
        )
    }
    TextButton(
        onClick = {
            FirebaseAuth().createUserWithCredential(textEmail.text,textPassword.text,textUserName.text,context, navController = navController)
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
        Text(text = "Create Account")
    }
}

@Preview
@Composable
fun PreviewLoginScreen(){
    SignUpScreen(navController = rememberNavController())
}