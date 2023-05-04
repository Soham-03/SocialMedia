package com.soham.socialmedia.firebase

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.soham.socialmedia.GlobalConstants
import com.soham.socialmedia.model.Comment
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.navigation.BottomBarScreen
import com.soham.socialmedia.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.HashMap

class FirebaseAuth() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    fun createUserWithCredential(
        email: String,
        password: String,
        username: String,
        context: Context,
        navController: NavController
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                        photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                    }
                    val hashMap = HashMap<String,String>()
                    hashMap["username"] = username
                    db.collection("users").document(auth.currentUser!!.uid).set(hashMap)
                        .addOnSuccessListener {
                            Log.d(TAG, "createUserWithEmail:success")
                            Toast.makeText(
                                context, "Success Creating User.",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Screens.MainScreen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                            } else {
                                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Failed Creating Account",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signInUserWithCredential(
        email: String,
        password: String,
        context: Context,
        navController: NavController
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(
                        context, "Signed In User",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    navController.navigate(Screens.MainScreen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun addUserInfoToFirebase(hashmap: HashMap<String, String>, context: Context) {
        var result = false
        db.collection("users").document(auth.currentUser!!.uid)
            .set(hashmap)
            .addOnCompleteListener {
                Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show()
            }
    }

    suspend fun addPostToDatabase(
        hashmap: HashMap<String, String>,
        context: Context,
        navController: NavController
    ) {
        db.collection("users").document(auth.currentUser!!.uid).collection("posts")
            .document().set(hashmap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show()
                    navController.navigate(BottomBarScreen.Home.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                } else {
                    Toast.makeText(context, "Unable to post", Toast.LENGTH_SHORT).show()
                }
            }
            .await()
    }

    @Composable
    fun getMyPosts(): SnapshotStateList<Post> {
        val listOfPosts = remember {
            mutableStateListOf<Post>()
        }
        db.collection("users").document(auth.currentUser!!.uid).collection("posts")
            .addSnapshotListener { value, error ->
                if (value!!.documents.isNotEmpty()) {
                    listOfPosts.removeRange(0, listOfPosts.size)
                    val docs = value.documents
                    if (docs.isNotEmpty()) {
                        for (doc in docs) {
                            val interestedUsersString = doc["interestedUsers"].toString()
                            val listOfInterestedUsers = interestedUsersString.split(", ") as MutableList
                            if(doc["type"].toString() == "Collab"){
                                listOfPosts.add(
                                    Post(
                                        FirebaseAuth.getInstance().currentUser!!.uid,
                                        doc.id,
                                        doc["content"].toString(),
                                        doc["title"].toString(),
                                        doc["type"].toString(),
                                        "",
                                        "",
                                        "",
                                        "",
                                        null,
                                        doc["postCollabInterests"].toString(),
                                        listOfInterestedUsers
                                    )
                                )
                            }
                            else{
                                val postImages = doc["postImages"].toString()
                                val imageStringArray = postImages.split(", ")
                                println("Images in array:$imageStringArray")
                                listOfPosts.add(
                                    Post(
                                        FirebaseAuth.getInstance().currentUser!!.uid,
                                        doc.id,
                                        doc["content"].toString(),
                                        doc["title"].toString(),
                                        doc["type"].toString(),
                                        doc["likes"].toString(),
                                        doc["dislikes"].toString(),
                                        doc["likedByMe"].toString(),
                                        doc["dislikedByMe"].toString(),
                                        imageStringArray,
                                        "",
                                        null
                                    )
                                )
                            }
                        }
                    }
                }
            }
        return listOfPosts
    }

    @Composable
    fun getAllPosts(type:String): SnapshotStateList<Post> {
        val listOfPosts = remember {
            mutableStateListOf<Post>()
        }
        for(user in GlobalConstants.users){
            if(user != FirebaseAuth.getInstance().currentUser!!.uid){
                db.collection("users").document(user).collection("posts").whereEqualTo("type",type)
                    .addSnapshotListener { value,error->
                        listOfPosts.clear()
                        println("Snapshot Called")
                        if (value!!.documents.isNotEmpty()) {
                            val docs = value.documents
                            if (docs.isNotEmpty()) {
                                for (doc in docs) {
                                    val interestedUsersString = doc["interestedUsers"].toString()
                                    val listOfInterestedUsers = interestedUsersString.split(", ") as MutableList
                                    if(doc["type"].toString() == "Collab"){
                                        listOfPosts.add(
                                            Post(
                                                user,
                                                doc.id,
                                                doc["content"].toString(),
                                                doc["title"].toString(),
                                                doc["type"].toString(),
                                                "",
                                                "",
                                                "",
                                                "",
                                                null,
                                                doc["postCollabInterests"].toString(),
                                                listOfInterestedUsers
                                            )
                                        )
                                    }
                                    else{
                                        val postImages = doc["postImages"].toString()
                                        val imageStringArray = postImages.split(", ")
                                        println("Images in array:$imageStringArray")
                                        listOfPosts.add(
                                            Post(
                                                user,
                                                doc.id,
                                                doc["content"].toString(),
                                                doc["title"].toString(),
                                                doc["type"].toString(),
                                                doc["likes"].toString(),
                                                doc["dislikes"].toString(),
                                                doc["likedByMe"].toString(),
                                                doc["dislikedByMe"].toString(),
                                                imageStringArray,
                                                "",
                                                null
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
            }
        }
        return listOfPosts
    }


    suspend fun getUsers(){
        var list = SnapshotStateList<String>()
        db.collection("users").get()
            .addOnCompleteListener { it->
                if (it.result.documents.isNotEmpty()) {
                    list.clear()
                    GlobalConstants.users.clear()
                    val docs = it.result.documents
                    if (docs.isNotEmpty()) {
                        for (doc in docs) {
                            list.add(doc.id)
                        }
                    }
                }
            }
            .await()
        GlobalConstants.users = list
    }


    fun updateLikes(post: Post, likes: String,state:String,dislikes:String,dislikeState:String) {
        val hashmap = HashMap<String,String>()
        hashmap["likes"] = likes
        hashmap["likedByMe"] = state
        if(state == "true" && dislikeState=="true"){
            hashmap["dislikedByMe"] = "false"
            hashmap["dislikes"] = (dislikes.toInt()-1).toString()
        }
        db.collection("users").document(post.ownerUid).collection("posts")
            .document(post.postId).update(hashmap as Map<String, Any>)
            .addOnSuccessListener {
            }
    }

    fun updateShowingInterests(post:Post,updatedInterestsNumber:String){
        val hashMap = HashMap<String,String>()
        hashMap["postCollabInterests"] = updatedInterestsNumber
        if(post.interestedUsers!!.isEmpty()){
            hashMap["interestedUsers"] = FirebaseAuth.getInstance().currentUser!!.uid
        }
        else{
            hashMap["interestedUsers"] = post.interestedUsers.joinToString()+", "+FirebaseAuth.getInstance().currentUser!!.uid
        }
        db.collection("users").document(post.ownerUid).collection("posts")
            .document(post.postId).update(hashMap as Map<String, Any>)
            .addOnSuccessListener {
                println("Interest Added")
            }
    }

    fun updateDisLikes(post: Post, likes: String,state:String,likesToDeduct:String,likeState:String) {
        val hashmap = HashMap<String,String>()
        hashmap["dislikes"] = likes
        hashmap["dislikedByMe"] = state
        if(state == "true" && likeState == "true"){
            hashmap["likedByMe"] = "false"
            hashmap["likes"] = (likesToDeduct.toInt()-1).toString()
        }
        db.collection("users").document(post.ownerUid).collection("posts")
            .document(post.postId).update(hashmap as Map<String, Any>)
            .addOnSuccessListener {
            }
    }

    suspend fun uploadImagesToDbStorage(list: List<Uri>, context: Context) {
        val storageRef = FirebaseStorage.getInstance()
        for (uri in list){
            val fileName = UUID.randomUUID().toString() +".jpg"
            storageRef.reference.child("${FirebaseAuth.getInstance().currentUser!!.uid}/$fileName")
                .putFile(uri)
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {url->
                        GlobalConstants.imagesToUploadLinks.add(url.toString())
                        println("Link:$url")
                    }
                }
                .await()
        }
    }

    suspend fun uploadImagesToDbStorage(uri: Uri, context: Context) {
        val storageRef = FirebaseStorage.getInstance()
        val fileName = UUID.randomUUID().toString() +".jpg"
        storageRef.reference.child("${FirebaseAuth.getInstance().currentUser!!.uid}/$fileName")
            .putFile(uri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {url->
                    GlobalConstants.imageToUploadLink = url.toString()
                    println("Link:$url")
                }
            }
            .await()
    }

//    @Composable
//    fun getAllComments(post:Post): SnapshotStateList<Comment> {
//        val listOfComments = remember {
//            mutableStateListOf<Comment>()
//        }
//        val listOfReplies = remember {
//            mutableStateListOf<Comment>()
//        }
//        db.collection("users").document(post.ownerUid)
//            .collection("posts").document(post.postId).collection("comments")
//            .addSnapshotListener { value, error ->
//                for(doc in value!!.documents){
//                    listOfComments.clear()
//                    db.collection("users").document(post.ownerUid)
//                        .collection("posts").document(post.postId).collection("comments")
//                        .document(doc.id).collection("replies")
//                        .addSnapshotListener { value1, error1 ->
//                            for(replyDoc in value1!!.documents){
//                                val list = SnapshotStateList<Comment>()
//                                listOfReplies.add(
//                                    Comment(
//                                        commentId = replyDoc.id,
//                                        userId = replyDoc["userId"].toString(),
//                                        comment = replyDoc["comment"].toString(),
//                                        replies = list
//                                    )
//                                )
//                            }
//                            listOfComments.add(
//                                Comment(
//                                    commentId = doc.id.toString(),
//                                    comment = doc["comment"].toString(),
//                                    userId = doc["userId"].toString(),
//                                    replies = listOfReplies
//                                    )
//                            )
//                        }
//                }
//            }
//        return listOfComments
//    }

    fun addComment(post: Post,comment:String,context: Context) {
        // Get a reference to the comments collection for the post
        val hashMap = HashMap<String,String>()
        hashMap["userId"] = FirebaseAuth.getInstance().currentUser!!.uid
        hashMap["username"] = auth.currentUser!!.displayName!!
        hashMap["comment"] = comment
       db.collection("users").document(post.ownerUid)
            .collection("posts").document(post.postId).collection("comments").document()
            .set(hashMap)
           .addOnSuccessListener {
               Toast.makeText(context, "Comment Posted", Toast.LENGTH_SHORT).show()
           }

    }

    fun addReply(post: Post,comment:Comment,reply:String,context:Context){
        val hashMap = HashMap<String,String>()
        hashMap["userId"] = FirebaseAuth.getInstance().currentUser!!.uid
        hashMap["username"] = auth.currentUser!!.displayName!!
        hashMap["comment"] = reply
        db.collection("users").document(post.ownerUid)
            .collection("posts").document(post.postId).collection("comments").document(comment.commentId)
            .collection("replies").document()
            .set(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context, "Comment Posted", Toast.LENGTH_SHORT).show()
            }
    }

    suspend fun getComments(post: Post): List<Comment> {
        val listOfComments = mutableListOf<Comment>()

        val commentsRef = db.collection("users").document(post.ownerUid)
            .collection("posts").document(post.postId).collection("comments")

        // Initial query to fetch all comments
        val snapshot = commentsRef.get().await()

        for (doc in snapshot.documents) {
            val listOfReplies = SnapshotStateList<Comment>()

            val repliesSnapshot = commentsRef.document(doc.id).collection("replies").get().await()
            for (replyDoc in repliesSnapshot.documents) {
                val list = SnapshotStateList<Comment>()
                listOfReplies.add(
                    Comment(
                        commentId = replyDoc.id,
                        userId = replyDoc["userId"].toString(),
                        username = replyDoc["username"].toString(),
                        comment = replyDoc["comment"].toString(),
                        replies = list
                    )
                )
            }

            listOfComments.add(
                Comment(
                    commentId = doc.id.toString(),
                    comment = doc["comment"].toString(),
                    userId = doc["userId"].toString(),
                    username = doc["username"].toString(),
                    replies = listOfReplies
                )
            )
        }

        commentsRef.addSnapshotListener { value, error ->
            GlobalScope.launch(Dispatchers.IO) {
                value?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        val doc = change.document
                        val listOfReplies = SnapshotStateList<Comment>()

                        val repliesSnapshot =
                            commentsRef.document(doc.id).collection("replies").get().await()

                        for (replyDoc in repliesSnapshot.documents) {
                            val list = SnapshotStateList<Comment>()
                            listOfReplies.add(
                                Comment(
                                    commentId = replyDoc.id,
                                    userId = replyDoc["userId"].toString(),
                                    comment = replyDoc["comment"].toString(),
                                    username = replyDoc["username"].toString(),
                                    replies = list
                                )
                            )
                        }

                        listOfComments.add(
                            Comment(
                                commentId = doc.id.toString(),
                                comment = doc["comment"].toString(),
                                userId = doc["userId"].toString(),
                                username = doc["username"].toString(),
                                replies = listOfReplies
                            )
                        )
                    }
                }
            }
        }
        return listOfComments
    }
}
