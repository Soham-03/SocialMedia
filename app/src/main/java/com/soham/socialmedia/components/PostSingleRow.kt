package com.soham.socialmedia.components

import android.content.Context
import android.text.TextUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.soham.socialmedia.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.model.Comment
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.ui.theme.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostSingleRow(post: Post,context: Context){
    if(post.type!="Collab"){
        var hasImage by remember{
            mutableStateOf(false)
        }
        var postImages by remember {
            mutableStateOf<Post?>(null)
        }
        var commentClicked by remember{
            mutableStateOf(false)
        }
        val listOfComments = remember {
            mutableStateListOf<Comment>()
        }
        LaunchedEffect(key1 = Unit) {
            val comments = FirebaseAuth().getComments(post)
            listOfComments.clear()
            listOfComments.addAll(comments)
        }
        fun refreshComments() {
            GlobalScope.launch {
                val comments = FirebaseAuth().getComments(post)
                listOfComments.clear()
                listOfComments.addAll(comments)
            }
        }
        postImages = post
        hasImage = post.postImages!!.isNotEmpty()
        println("GGWP: ${post.postImages}")

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = Color.Transparent,
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp
        ) {
            val commentImage:Int = if(!commentClicked){
                R.drawable.ic_comment_filled
            } else{
                R.drawable.ic_comment_filled
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ){

                    Image(
                        painter = painterResource(id = R.drawable.ic_pfp_placeholder),
                        contentDescription = "User's profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 60.dp, height = 60.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = post.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                ){
                    var liked by remember {
                        mutableStateOf(post.likedByMe)
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                    ){
                        val likeImage:Int = if(post.likedByMe == "true"){
                            R.drawable.ic_like_filled
                        } else{
                            R.drawable.ic_like
                        }
                        Image(
                            painter = painterResource(id = likeImage),
                            contentDescription = "Like Button",
                            colorFilter = ColorFilter.tint(ChipSelectedColor),
                            modifier = Modifier
                                .size(24.dp, 24.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    if (post.likedByMe == "false") {
                                        FirebaseAuth().updateLikes(
                                            post = post,
                                            likes = (post.likes.toInt() + 1).toString(),
                                            state = "true",
                                            dislikes = post.dislikes,
                                            dislikeState = post.dislikedByMe
                                        )
                                    } else if (post.likedByMe == "true") {
                                        FirebaseAuth().updateLikes(
                                            post = post,
                                            likes = (post.likes.toInt() - 1).toString(),
                                            state = "false",
                                            dislikes = post.dislikes,
                                            dislikeState = post.dislikedByMe
                                        )
                                    }
                                }
                        )
                        Text(
                            text=post.likes,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                        )
                        Text(
                            text=post.dislikes,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(0.dp, 8.dp, 0.dp, 8.dp)
                        )
                        val dislikeImage:Int = if(post.dislikedByMe == "true"){
                            R.drawable.ic_like_filled
                        } else{
                            R.drawable.ic_like
                        }
                        Image(
                            painter = painterResource(id = dislikeImage),
                            contentDescription = "Dislike Button",
                            colorFilter = ColorFilter.tint(ChipSelectedColor),
                            modifier = Modifier
                                .size(24.dp, 24.dp)
                                .align(Alignment.CenterHorizontally)
                                .rotate(180f)
                                .clickable {
                                    if (post.dislikedByMe == "false") {
                                        FirebaseAuth().updateDisLikes(
                                            post = post,
                                            likes = (post.dislikes.toInt() + 1).toString(),
                                            state = "true",
                                            likesToDeduct = post.likes,
                                            likeState = post.likedByMe
                                        )
                                    } else if (post.dislikedByMe == "true") {
                                        FirebaseAuth().updateDisLikes(
                                            post = post,
                                            likes = (post.dislikes.toInt() - 1).toString(),
                                            state = "false",
                                            likesToDeduct = post.likes,
                                            likeState = post.likedByMe
                                        )
                                    }
                                }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = commentImage),
                            contentDescription = "Comments Button",
                            modifier = Modifier
                                .size(24.dp, 24.dp)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    commentClicked = !commentClicked
                                }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = post.content,
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(12.dp,12.dp,0.dp,16.dp)
                        )
                        var loading by remember{
                            mutableStateOf(false)
                        }
                        AnimatedVisibility(
                            visible = hasImage
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(320.dp)
                                    .height(240.dp)
                                    .padding(12.dp, 12.dp, 0.dp, 16.dp),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                PostsCarousel(
                                    itemsCount = postImages!!.postImages!!.size,
                                    itemContent = { index ->
                                        AnimatedVisibility(visible = loading) {
                                            CircularProgressIndicator()
                                        }
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(postImages!!.postImages?.get(index))
                                                .build(),
                                            contentDescription = "Post Images",
                                            contentScale = ContentScale.Crop,
                                            onLoading = {
                                                loading = true
                                            },
                                            onSuccess = {
                                                loading = false
                                            },
                                            error = painterResource(id = R.drawable.ic_apple_logo),
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clickable {
                                                    println("Clicked on index:" + postImages!!.postImages!![index])
                                                }
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    visible = commentClicked,
                    enter = fadeIn(animationSpec = tween(1000)),
                    exit = fadeOut(animationSpec = tween(1000))
                ) {
                    Column(
                        Modifier.background(CardGradient1)
                    ){
                        var commentText by remember{
                            mutableStateOf(TextFieldValue(""))
                        }
                        var placeholderText by remember{
                            mutableStateOf("Enter Comment")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            OutlinedTextField(
                                value = commentText,
                                onValueChange = {
                                    commentText = it
                                },
                                placeholder = {
                                    Text(
                                        text = placeholderText,
                                        fontSize = 14.sp
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = ChipBackgroundColor,
                                    textColor = Color.White,
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = Color.White,
                                    placeholderColor = Color.Gray
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .width(250.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_send), 
                                contentDescription = "Send Button",
                                colorFilter = ColorFilter.tint(ChipSelectedColor),
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        if(GlobalConstants.currentSelectedCommentToReply!=null){
                                            FirebaseAuth().addReply(post,GlobalConstants.currentSelectedCommentToReply!!,commentText.text,context)
                                            refreshComments()
                                            commentText = TextFieldValue("")
                                        }
                                        if(!TextUtils.isEmpty(commentText.text)){
                                            FirebaseAuth().addComment(post,commentText.text,
                                                context)
                                            refreshComments()
                                            commentText = TextFieldValue("")
                                        }
                                    }
                            )
                        }
                        if(commentClicked){
                            LazyColumn(
                                modifier = Modifier
                                    .height(200.dp)
                                    .background(Color.Transparent)
                            ){
                                items(listOfComments){comment->
                                    CommentSingleRow(comment = comment)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    else{
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = Color.Transparent,
            shape = RoundedCornerShape(20.dp),
            elevation = 0.dp
        ) {
            Column(
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
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ){

                    Image(
                        painter = painterResource(id = R.drawable.ic_pfp_placeholder),
                        contentDescription = "User's profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 60.dp, height = 60.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = post.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = post.content,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(12.dp,12.dp,0.dp,16.dp)
                    )
                    CollabPostSingleRow(post = post,context)
                }

            }
        }
    }
}

@Preview
@Composable
fun preview(){
//    PostSingleRow(post = Post("","GGWP","asdasdasdasd","asd","10","69","true","false","",emptyList<String>() as ArrayList<String>,"",
//        emptyList()))
}