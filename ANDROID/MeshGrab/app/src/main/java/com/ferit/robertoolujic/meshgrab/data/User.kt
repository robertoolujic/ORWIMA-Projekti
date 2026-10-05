package com.ferit.robertoolujic.meshgrab.data

import com.google.firebase.Timestamp
data class User(
    public var id : String = "",
    public val name : String = "",
    public val bio : String = "",
    public val avatar : String = "",
    public val registrationDate : Timestamp = Timestamp.now(),
    public val following : Int = 0,
    public val followers : Int = 0,
    public val likes : Int = 0,
    public val downloads : Int = 0,
    public val makes : Int = 0,
    public val modelGallery : List<String> = listOf()
)