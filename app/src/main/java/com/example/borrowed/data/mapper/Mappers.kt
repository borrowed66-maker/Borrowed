package com.example.borrowed.data.mapper


import com.example.borrowed.data.local.entity.BorrowedItemEntity
import com.example.borrowed.data.remote.dto.BorrowedItemDto
import com.example.borrowed.domain.model.BorrowedItem
import com.example.borrowed.domain.model.ItemStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

// DTO -> Domain
fun BorrowedItemDto.toDomain(): BorrowedItem = BorrowedItem(
    id = id,
    userId = user_id,
    itemName = item_name,
    borrowerName = borrower_name,
    contactNumber = contact_number,
    dateLent = LocalDate.parse(date_lent, dateFormatter),
    expectedReturnDate = LocalDate.parse(expected_return_date, dateFormatter),
    notes = notes,
    status = try { ItemStatus.valueOf(status.uppercase()) } catch (e: Exception) { ItemStatus.ACTIVE },
    createdAt = try { LocalDateTime.parse(created_at, dateTimeFormatter) } catch (e: Exception) { LocalDateTime.now() },
    updatedAt = try { LocalDateTime.parse(updated_at, dateTimeFormatter) } catch (e: Exception) { LocalDateTime.now() },
    isSynced = true
)

// Domain -> DTO
fun BorrowedItem.toDto(): BorrowedItemDto = BorrowedItemDto(
    id = id,
    user_id = userId,
    item_name = itemName,
    borrower_name = borrowerName,
    contact_number = contactNumber,
    date_lent = dateLent.format(dateFormatter),
    expected_return_date = expectedReturnDate.format(dateFormatter),
    notes = notes,
    status = status.name.lowercase(),
    created_at = createdAt.format(dateTimeFormatter),
    updated_at = updatedAt.format(dateTimeFormatter)
)

// Domain -> Entity
fun BorrowedItem.toEntity(): BorrowedItemEntity = BorrowedItemEntity(
    id = id,
    userId = userId,
    itemName = itemName,
    borrowerName = borrowerName,
    contactNumber = contactNumber,
    dateLent = dateLent.format(dateFormatter),
    expectedReturnDate = expectedReturnDate.format(dateFormatter),
    notes = notes,
    status = status.name,
    createdAt = createdAt.format(dateTimeFormatter),
    updatedAt = updatedAt.format(dateTimeFormatter),
    isSynced = isSynced
)

// Entity -> Domain
fun BorrowedItemEntity.toDomain(): BorrowedItem = BorrowedItem(
    id = id,
    userId = userId,
    itemName = itemName,
    borrowerName = borrowerName,
    contactNumber = contactNumber,
    dateLent = LocalDate.parse(dateLent, dateFormatter),
    expectedReturnDate = LocalDate.parse(expectedReturnDate, dateFormatter),
    notes = notes,
    status = try { ItemStatus.valueOf(status) } catch (e: Exception) { ItemStatus.ACTIVE },
    createdAt = try { LocalDateTime.parse(createdAt, dateTimeFormatter) } catch (e: Exception) { LocalDateTime.now() },
    updatedAt = try { LocalDateTime.parse(updatedAt, dateTimeFormatter) } catch (e: Exception) { LocalDateTime.now() },
    isSynced = isSynced
)