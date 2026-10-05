package com.ferit.robertoolujic.meshgrab.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ModelViewModel : ViewModel() {
    private val db = Firebase.firestore
    val modelsData = mutableStateListOf<Model>()

    init{
        fetchDatabaseData()
    }
    fun addLike(modelId : String){
        val modelDoc = db.collection("models").document(modelId)
        modelDoc.update(
            "likes",
            FieldValue.increment(1))
    }
    fun removeLike(modelId : String){
        val modelDoc = db.collection("models").document(modelId)
        modelDoc.update(
            "likes",
            FieldValue.increment(-1))
    }
    fun addModel(model: Model, callback: (String) -> Unit){
        db.collection("models").add(model)
            .addOnSuccessListener { documentReference ->
                val modelId = documentReference.id
                callback(modelId)
            }
    }
    private fun fetchDatabaseData(){
        db.collection("models")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot == null) return@addSnapshotListener
                modelsData.clear()
                for(document in snapshot.documents){
                    val model = document.toObject(Model::class.java)

                    if (model!=null){
                        model.id = document.id
                        modelsData.add(model)
                    }
                }
            }
    }
}