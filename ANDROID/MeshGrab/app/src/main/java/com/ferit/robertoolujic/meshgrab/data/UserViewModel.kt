package com.ferit.robertoolujic.meshgrab.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class UserViewModel : ViewModel() {
    private val db = Firebase.firestore
    val usersData = mutableStateListOf<User>()

    init{
        fetchDatabaseData()
    }
    fun addLike(userId: String){
        val userDoc = db.collection("users").document(userId)
        userDoc.update(
            "likes",
            FieldValue.increment(1))
    }
    fun removeLike(userId: String){
        val userDoc = db.collection("users").document(userId)
        userDoc.update(
            "likes",
            FieldValue.increment(-1))
    }
    private fun fetchDatabaseData(){
        db.collection("users")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot == null) return@addSnapshotListener
                usersData.clear()
                for(document in snapshot.documents){
                    val user = document.toObject(User::class.java)

                    if (user!=null){
                        user.id = document.id
                        usersData.add(user)
                    }
                }
            }
    }
}