package com.soham.socialmedia.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.soham.socialmedia.components.PostSingleRow
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.ui.theme.*

@Composable
fun HomeScreen(){
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val listOfPosts = remember{mutableStateListOf<Post?>()}

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(0.dp, 0.dp, 0.dp, 58.dp)
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

//        db.collection("users").document(auth.currentUser!!.uid).collection("posts").get()
//            .addOnSuccessListener { queryDocumentSnapshots ->
//                // after getting the data we are
//                // calling on success method
//                // and inside this method we are
//                // checking if the received query
//                // snapshot is empty or not.
//                if (!queryDocumentSnapshots.isEmpty) {
//                    // if the snapshot is not empty we are
//                    // hiding our progress bar and adding
//                    // our data in a list.
//                    // loadingPB.setVisibility(View.GONE)
//                    val list = queryDocumentSnapshots.documents
//                    for (d in list) {
//                        // after getting this list we are passing that list
//                        // to our object class.
//                        val post: Post? = d.toObject(Post::class.java)
//                        // and we will pass this object class
//                        // inside our arraylist which we have
//                        // created for list view.
//                        listOfPosts.add(post)
//
//                    }
//                } else {
//
//                }
//            }
//            // if we don't get any data
//            // or any error we are displaying
//            // a toast message that we donot get any data
//            .addOnFailureListener {
////                Toast.makeText(
////                    ,
////                    "Fail to get the data.",
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
        val list = com.soham.socialmedia.firebase.FirebaseAuth().getMyPosts()
        LazyColumn(
        contentPadding = PaddingValues(22.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        itemsIndexed(list){index,post->
            PostSingleRow(post)
        }
    }
}

//@Composable
//private fun PostsList(list:mutable){
////    LazyColumn(
////        contentPadding = PaddingValues(22.dp),
////        verticalArrangement = Arrangement.spacedBy(22.dp),
////        modifier = Modifier
////            .fillMaxHeight()
////            .fillMaxWidth()
////    ){
////        itemsIndexed(list){index,post->
////            PostSingleRow(post)
////        }
////        itemsIndexed(items = list){post->
////            PostSingleRow(post)
////        }
//    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}