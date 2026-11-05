package com.example.borrowed.data.remote.dto


import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String = "",
    val email: String = "",
    val created_at: String = ""
)