package com.example.borrowed.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BorrowedItemDto(
    val id: String = "",
    val user_id: String = "",
    val item_name: String = "",
    val borrower_name: String = "",
    val contact_number: String = "",
    val date_lent: String = "",
    val expected_return_date: String = "",
    val notes: String = "",
    val status: String = "",
    val created_at: String = "",
    val updated_at: String = ""
)