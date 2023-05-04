package com.soham.socialmedia.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.R
import com.soham.socialmedia.components.PostsCarousel
import com.soham.socialmedia.firebase.FirebaseAuth
import com.soham.socialmedia.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

private var currentChip = 1
@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun AddPostScreen(navController: NavController){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    )
    requestStoragePermission(context = context, permissionsState = permissionsState)
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver{_,event->
            if(event == Lifecycle.Event.ON_RESUME){
                permissionsState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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
                    color = Color.Gray,
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
                    text = "Content goes\nhere..",
                    color = Color.Gray,
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
        var automaticImages by remember{
            mutableStateOf<List<Uri>>(emptyList())
        }
        var postsToUpload by remember{
            mutableStateOf<List<Uri>>(emptyList())
        }
        automaticImages = getPhotos()

        //

        var visible by remember {
            mutableStateOf(true)
        }
        var visibleSelectedImage by remember {
            mutableStateOf(false)
        }
        var visibleMultipleImage by remember {
            mutableStateOf(false)
        }
        var selectedUri by remember{
            mutableStateOf<Uri?>(null)
        }
        var selectedImageUris by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        val photoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris ->
                selectedImageUris = uris
                if(selectedImageUris.isNotEmpty()){
                    visibleMultipleImage = true
                    visibleSelectedImage = false
                }
            })
        visible = automaticImages.isNotEmpty()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimatedVisibility(visible = visibleSelectedImage ) {
                Card(
                    contentColor = CardGradient1,
                    backgroundColor = BackgroundGradient2,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(320.dp)
                        .height(248.dp)
                )
                {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedUri)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .width(320.dp)
                            .height(240.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            AnimatedVisibility(visible = visibleMultipleImage ){
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .height(240.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    PostsCarousel(
                        itemsCount = selectedImageUris.size,
                        itemContent = { index ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(selectedImageUris[index])
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(6.dp)
                ){
                    item {
                        Card(
                            contentColor = CardGradient1,
                            backgroundColor = BackgroundGradient2,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(90.dp)
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "camera",
                                colorFilter = ColorFilter.tint(CardGradient1),
                                modifier = Modifier.padding()
                            )
                        }
                    }
                    items(automaticImages){uri->
                        Card(
                            contentColor = CardGradient1,
                            backgroundColor = BackgroundGradient2,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(90.dp)
                                .clickable {
                                    visibleSelectedImage = true
                                    visibleMultipleImage = false
                                    selectedUri = uri
                                }
                        )
                        {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uri)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    item {
                        Card(
                            contentColor = CardGradient1,
                            backgroundColor = BackgroundGradient2,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(90.dp)
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.ic_more_images),
                                contentDescription = "more images",
                                colorFilter = ColorFilter.tint(CardGradient1),
                                modifier = Modifier.padding()
                            )
                        }
                    }
                }
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = automaticImages.isEmpty(),
            enter = fadeIn(
                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            Text(
                text = "Please allow the required permissions from app's settings. Click here to proceed",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        val i = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + context.packageName)
                        )
                        context.startActivity(i)
                    }
            )
        }

        //

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
                            hashmap["likes"] = "0"
                            hashmap["dislikes"] = "0"
                            hashmap["likedByMe"] = "false"
                            hashmap["dislikedByMe"] = "false"
                            if(selectedImageUris.isNotEmpty()){
                                println(selectedImageUris)
                                GlobalScope.launch {
                                    FirebaseAuth().uploadImagesToDbStorage(selectedImageUris,context)
                                    if(GlobalConstants.imagesToUploadLinks.isNotEmpty()){
                                        hashmap["postImages"] = GlobalConstants.imagesToUploadLinks.joinToString()
                                        FirebaseAuth().addPostToDatabase(hashmap = hashmap, context = context, navController = navController)
                                    }
                                    else{
                                    }
                                }
                            }
                            else{
                                GlobalScope.launch {
                                    println("Selected Image: "+selectedUri!!.path)
                                    if(!TextUtils.isEmpty(selectedUri.toString())){
                                        FirebaseAuth().uploadImagesToDbStorage(selectedUri!!,context)
                                        if(GlobalConstants.imageToUploadLink!=null){
                                            hashmap["postImages"] = GlobalConstants.imageToUploadLink!!
                                            FirebaseAuth().addPostToDatabase(hashmap = hashmap, context = context, navController = navController)
                                        }
                                    }
                                    else{
//                                        FirebaseAuth().addPostToDatabase(hashmap = hashmap, context = context, navController = navController)
                                    }
                                }
                            }

                        }
                        2 -> {
                            hashmap["type"] = "Doubts"
                            hashmap["likes"] = "0"
                            hashmap["dislikes"] = "0"
                            hashmap["likedByMe"] = "false"
                            hashmap["dislikedByMe"] = "false"
                            if(selectedImageUris.isNotEmpty()){
                                GlobalScope.launch {
                                    FirebaseAuth().uploadImagesToDbStorage(selectedImageUris,context)
                                    if(GlobalConstants.imagesToUploadLinks.isNotEmpty()){
                                        hashmap["postImages"] = GlobalConstants.imagesToUploadLinks.joinToString()
                                        FirebaseAuth().addPostToDatabase(hashmap = hashmap, context = context, navController = navController)
                                    }
                                }
                            }
                        }
                        else -> {
                            hashmap["type"] = "Collab"
                            hashmap["likes"] = "0"
                            hashmap["dislikes"] = "0"
                            hashmap["likedByMe"] = "false"
                            hashmap["dislikedByMe"] = "false"
                            hashmap["postCollabInterests"] = "0"
                            hashmap["interestedUsers"] = ""
                            GlobalScope.launch {
                                FirebaseAuth().addPostToDatabase(hashmap = hashmap, context = context, navController = navController)
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp, 10.dp,22.dp,60.dp)
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

@Composable
private fun getPhotos(): List<Uri> {
    val photos  = remember { mutableStateListOf<Uri>() }
    val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
    val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
    val context = LocalContext.current
    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        null,
        null,
        sortOrder
    )


    cursor?.use {
        while (cursor.moveToNext() && photos.size < 10) {
            if(cursor.getColumnIndex(MediaStore.Images.Media.DATA)>0){
                val uri = Uri.fromFile(File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).toUri().toString()))
                try {
                    photos.add(uri)
                } catch (e: IOException) {
                    // Handle error
                }
            }

        }
    }
    println("Photos: "+photos.size)
    return photos
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun requestStoragePermission(context: Context,permissionsState: MultiplePermissionsState){
    permissionsState.permissions.forEach { perm->
        when(perm.permission){
            Manifest.permission.READ_EXTERNAL_STORAGE->{
                when{
                    perm.hasPermission->{
                        println("Permission already granted")
                    }
                    perm.shouldShowRationale->{
                        Toast.makeText(context, "We need to have permission to access your camera and storage to let you post images.", Toast.LENGTH_SHORT).show()
                    }
                    perm.hasPermission && !perm.shouldShowRationale->{
                        Toast.makeText(context, "Permissions are permanently denied. You can enable them in the app settings.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun AddPostScreenPreview(){
    AddPostScreen(rememberNavController())
}