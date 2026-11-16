package com.borrowed.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Stores the details of an item lent to another person.
 *
 * Dates use the yyyy-MM-dd format.
 */
@Serializable
data class BorrowedItem(
    val id: String? = null,

    @SerialName("user_id")
    val userId: String,

    @SerialName("item_name")
    val itemName: String,

    @SerialName("borrower_name")
    val borrowerName: String,

    @SerialName("contact_number")
    val contactNumber: String? = null,

    @SerialName("lent_date")
    val lentDate: String,

    @SerialName("return_date")
    val returnDate: String,

    val notes: String? = null,

    @SerialName("is_returned")
    val isReturned: Boolean = false,

    @SerialName("created_at")
    val createdAt: String? = null
)
