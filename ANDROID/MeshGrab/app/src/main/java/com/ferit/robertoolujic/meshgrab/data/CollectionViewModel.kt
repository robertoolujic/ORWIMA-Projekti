package com.ferit.robertoolujic.meshgrab.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class CollectionViewModel : ViewModel() {
    private val db = Firebase.firestore
    val collectionsData = mutableStateListOf<Collection>()

    init{
        fetchDatabaseData()
    }
    fun IsInCollection(collectionId : String, modelId : String) : Boolean{
        return collectionsData.first { it.id == collectionId }.models.contains(modelId)
    }
    fun AddModel(collectionId : String, modelId : String){
        val collectionModels = collectionsData.first { it.id == collectionId }.models
        val collectionDoc = db.collection("collections").document(collectionId)
        if(collectionModels.contains(modelId)) return
        else collectionDoc.update(
            "models",
            FieldValue.arrayUnion(modelId))
    }
    fun RemoveModel(collectionId : String, modelId : String){
        val collectionModels = collectionsData.first { it.id == collectionId }.models
        val collectionDoc = db.collection("collections").document(collectionId)
        if(!collectionModels.contains(modelId)) return
        else collectionDoc.update(
            "models",
            FieldValue.arrayRemove(modelId))
    }
    fun ToggleModel(collectionId : String, modelId : String){
        val collectionModels = collectionsData.first { it.id == collectionId }.models
        val collectionDoc = db.collection("collections").document(collectionId)
        if(collectionModels.contains(modelId))
            collectionDoc.update(
                "models",
                FieldValue.arrayRemove(modelId))
        else collectionDoc.update(
            "models",
            FieldValue.arrayUnion(modelId))
    }
    private fun fetchDatabaseData(){
        db.collection("collections")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot == null) return@addSnapshotListener
                collectionsData.clear()
                for (document in snapshot.documents) {
                    val collection = document.toObject(Collection::class.java)
                    if (collection != null) {
                        collection.id = document.id
                        collectionsData.add(collection)
                    }
                }
            }
    }
}