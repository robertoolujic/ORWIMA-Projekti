package com.ferit.robertoolujic.meshgrab.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Model(
    @get:Exclude @set:Exclude
    public var id : String = "",

    public val title : String = "",
    public val description : String = "",
    public val creatorId : String = "",
    public val likes : Int = 0,
    public val downloads : Int = 0,
    public val makes : Int = 0,
    public val dateUploaded : Timestamp = Timestamp.now(),
    public val gallery : List<String> = listOf(),
    public val files : List<String> = listOf(),

)