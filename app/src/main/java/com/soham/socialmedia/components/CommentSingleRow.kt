package com.soham.socialmedia.components

import android.content.res.Resources
import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.R
import com.soham.socialmedia.model.Comment
import com.soham.socialmedia.ui.theme.*

@Composable
fun CommentSingleRow(comment: Comment){
    var commentState by remember {
        mutableStateOf<Comment?>(null)
    }
    var replyState by remember{
        mutableStateOf(false)
    }
    var textOfReply by remember {
        mutableStateOf("Reply")
    }
    if(replyState){
        textOfReply = "Replying to\n"+comment.commentId+"\nClick again to cancel"
        commentState = comment
        GlobalConstants.currentSelectedCommentToReply = commentState
    }
    else{
        textOfReply = "Reply"
        commentState = comment
        GlobalConstants.currentSelectedCommentToReply = commentState
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(CardGradient1)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(CardGradient1)
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "User's profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 32.dp, height = 32.dp)
                    .clip(CircleShape)
            )
            Text(
                text = comment.username,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(12.dp, 0.dp)
            )
        }
        Column(
        ){
            Text(
                text = commentState!!.comment,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 0.dp, 12.dp, 0.dp)
            )
            Text(
                text=textOfReply,
                color = ChipSelectedColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(12.dp, 0.dp, 12.dp, 12.dp)
                    .clickable {
                        replyState = !replyState
                    }
                    .onFocusChanged {
                        replyState = it.hasFocus
                    }
            )
        }
        LazyColumn(
            modifier = Modifier
                .height((comment.replies.size * 90).dp)
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .background(CardGradient1)
        ){
            items(comment.replies){comm->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(22.dp,0.dp,8.dp,0.dp)
                ){
                    Box(
                        modifier = Modifier
                            .width(10.dp)
                            .height((comment.replies.size * 90/comment.replies.size).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ChipSelectedColor)
                    )
                    Column(){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(22.dp,0.dp)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "User's profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(width = 32.dp, height = 32.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = comm.username,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(12.dp, 0.dp)
                            )
                        }
                        Text(
                            text = comm.comment,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier
                                .padding(60.dp, 0.dp, 12.dp, 0.dp)
                        )
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewCommentSingleRow(){
//    CommentSingleRow(comment = Comment("","","this is my first comment here",
//        emptyList<Comment>() as ArrayList<Comment>
//    ))
}