package com.soham.socialmedia.firebase

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.soham.socialmedia.model.Post
import com.soham.socialmedia.navigation.BottomBarScreen
import com.soham.socialmedia.navigation.Screens

class FirebaseAuth(){
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    fun createUserWithCredential(email:String,password:String,username:String,context:Context,navController: NavController){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                        photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                Log.d(TAG, "createUserWithEmail:success")
                                Toast.makeText(context, "Success Creating User.",
                                    Toast.LENGTH_SHORT).show()
                                navController.navigate(Screens.MainScreen.route){
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                            else{
                                Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show()
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Failed Creating Account",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signInUserWithCredential(email:String,password:String,context:Context,navController: NavController){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(context, "Signed In User",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    navController.navigate(Screens.MainScreen.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun addUserInfoToFirebase(hashmap:HashMap<String,String>,context:Context): Boolean {
        var result = false
        db.collection("users").document(auth.currentUser!!.uid)
            .set(hashmap)
            .addOnCompleteListener {
                result = if(it.isSuccessful){
                    Toast.makeText(context, "Profile Info updated", Toast.LENGTH_SHORT).show()
                    true
                } else{
                    false
                }
            }
        return result
    }

    fun addPostToDatabase(hashmap:HashMap<String,String>,context:Context,navController: NavController){
        db.collection("users").document(auth.currentUser!!.uid).collection("posts")
            .document().set(hashmap)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show()
                    navController.navigate(BottomBarScreen.Home.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                } else{
                    Toast.makeText(context,"Unable to post",Toast.LENGTH_SHORT).show()
                }
            }
    }

    @Composable
    fun getMyPosts(): SnapshotStateList<Post> {
        val listOfPosts = remember{
            mutableStateListOf<Post>()
        }
        db.collection("users").document(auth.currentUser!!.uid).collection("posts")
            .addSnapshotListener { value, error ->
                db.collection("users").document(auth.currentUser!!.uid).collection("posts")
                    .get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val docs = it.result.documents
                            if(docs.isNotEmpty()){
                                for(doc in docs){
                                    listOfPosts.add(Post(
                                        doc["content"].toString(),
                                        doc["title"].toString(),
                                        ""
                                    ))
                                }
                            }
                        }
                    }
            }
        return listOfPosts
    }

}
