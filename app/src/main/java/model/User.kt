package com.example.myappmusic.login

data class SignInResult(
    val data: User?,
    val errorMessage:String?
)

data class User(
    val userId:String,
    val userName:String,
    val profilePictureUrl:String?
)
