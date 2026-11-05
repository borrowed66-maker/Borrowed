package com.example.borrowed.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "borrowed_items")
data class BorrowedItemEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val itemName: String,
    val borrowerName: String,
    val contactNumber: String = "",
    val dateLent: String,
    val expectedReturnDate: String,
    val notes: String = "",
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val isSynced: Boolean = false
)