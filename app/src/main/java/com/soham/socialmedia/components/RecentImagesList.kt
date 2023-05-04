package com.soham.socialmedia.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.common.reflect.Reflection.getPackageName
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.R
import com.soham.socialmedia.ui.theme.BackgroundGradient2
import com.soham.socialmedia.ui.theme.CardGradient1
import java.util.logging.Logger.global

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentImagesList(list: List<Uri>) {
}


@Preview
@Composable
fun RecentImagesPreview(){
    RecentImagesList(list = emptyList())
}

