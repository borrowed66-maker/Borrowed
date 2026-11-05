package com.example.borrowed.data.local.dao


import androidx.room.*
import com.example.borrowed.data.local.entity.BorrowedItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrowedItemDao {

    @Query("SELECT * FROM borrowed_items WHERE userId = :userId ORDER BY expectedReturnDate ASC")
    fun getAllItemsByUser(userId: String): Flow<List<BorrowedItemEntity>>

    @Query("SELECT * FROM borrowed_items WHERE userId = :userId AND status = :status ORDER BY expectedReturnDate ASC")
    fun getItemsByStatus(userId: String, status: String): Flow<List<BorrowedItemEntity>>

    @Query("SELECT * FROM borrowed_items WHERE id = :itemId")
    suspend fun getItemById(itemId: String): BorrowedItemEntity?

    @Query("SELECT * FROM borrowed_items WHERE userId = :userId AND isSynced = 0")
    suspend fun getUnsyncedItems(userId: String): List<BorrowedItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: BorrowedItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<BorrowedItemEntity>)

    @Update
    suspend fun updateItem(item: BorrowedItemEntity)

    @Query("DELETE FROM borrowed_items WHERE id = :itemId")
    suspend fun deleteItem(itemId: String)

    @Query("DELETE FROM borrowed_items WHERE userId = :userId")
    suspend fun deleteAllItems(userId: String)

    @Query("UPDATE borrowed_items SET isSynced = 1 WHERE id = :itemId")
    suspend fun markAsSynced(itemId: String)

    @Query("UPDATE borrowed_items SET status = :status, updatedAt = :updatedAt WHERE id = :itemId")
    suspend fun updateItemStatus(itemId: String, status: String, updatedAt: String)
}