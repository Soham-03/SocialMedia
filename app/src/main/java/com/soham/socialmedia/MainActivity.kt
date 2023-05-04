package com.soham.socialmedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.soham.socialmedia.navigation.NavigationGraph
import com.soham.socialmedia.navigation.Screens
import com.soham.socialmedia.ui.theme.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                    FirebaseApp.initializeApp(this)
                    val user = FirebaseAuth.getInstance().currentUser
                    if(user!=null){
                       com.soham.socialmedia.navigation.BottomNavigation()
                        LaunchedEffect(key1 = true, key2 = true, key3 = true) {
                            com.soham.socialmedia.firebase.FirebaseAuth().getUsers()
                        }
                    }
                    else{
                        NavigationGraph()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SocialMediaTheme {
        com.soham.socialmedia.navigation.BottomNavigation()
    }
}