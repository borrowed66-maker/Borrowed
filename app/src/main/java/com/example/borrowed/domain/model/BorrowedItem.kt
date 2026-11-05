package com.example.borrowed.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class BorrowedItem(
    val id: String = "",
    val userId: String = "",
    val itemName: String = "",
    val borrowerName: String = "",
    val contactNumber: String = "",
    val dateLent: LocalDate = LocalDate.now(),
    val expectedReturnDate: LocalDate = LocalDate.now().plusDays(7),
    val notes: String = "",
    val status: ItemStatus = ItemStatus.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isSynced: Boolean = false
) {
    val isOverdue: Boolean
        get() = status == ItemStatus.ACTIVE && expectedReturnDate.isBefore(LocalDate.now())
}