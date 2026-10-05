package com.ferit.robertoolujic.meshgrab.data

import com.google.firebase.Timestamp

data class Collection(
    public var id : String = "",
    public val title : String = "",
    public val description : String = "",
    public val creatorId : String = "",
    public val likes : Int = 0,
    public val downloads : Int = 0,
    public val makes : Int = 0,
    public val private : Boolean = false,
    public val dateUploaded : Timestamp = Timestamp.now(),
    public val gallery : List<String> = listOf(),
    public val models : List<String> = listOf()
)
