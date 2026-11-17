package com.borrowed.app.data.repository

import com.borrowed.app.data.model.BorrowedItem
import com.borrowed.app.data.remote.SupabaseClientProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.currentUserOrNull
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.eq
import io.github.jan.supabase.postgrest.result.decodeList
import io.github.jan.supabase.postgrest.result.decodeSingle

/**
 * Handles create, read, update and delete operations for lending records.
 * Supabase Row Level Security remains the final access-control check.
 */
class BorrowedItemRepository(
    private val client: SupabaseClient = SupabaseClientProvider.client
) {

    /** Adds a record for the currently signed-in user. */
    suspend fun addItem(item: BorrowedItem): Result<BorrowedItem> = runCatching {
        val userId = requireSignedInUserId()
        val itemToSave = item.copy(
            id = null,
            userId = userId,
            createdAt = null
        )

        client.from(TABLE_NAME)
            .insert(itemToSave) {
                select()
            }
            .decodeSingle<BorrowedItem>()
    }

    /** Returns only the current user's lending records. */
    suspend fun getItems(): Result<List<BorrowedItem>> = runCatching {
        val userId = requireSignedInUserId()

        client.from(TABLE_NAME)
            .select {
                filter {
                    eq("user_id", userId)
                }
            }
            .decodeList<BorrowedItem>()
    }

    /** Updates an existing record owned by the currently signed-in user. */
    suspend fun updateItem(item: BorrowedItem): Result<Unit> = runCatching {
        val userId = requireSignedInUserId()
        val itemId = requireNotNull(item.id) { "An item ID is required to update a record." }

        client.from(TABLE_NAME).update(
            item.copy(userId = userId)
        ) {
            filter {
                eq("id", itemId)
                eq("user_id", userId)
            }
        }
    }

    /** Deletes the specified record if it belongs to the current user. */
    suspend fun deleteItem(itemId: String): Result<Unit> = runCatching {
        val userId = requireSignedInUserId()

        client.from(TABLE_NAME).delete {
            filter {
                eq("id", itemId)
                eq("user_id", userId)
            }
        }
    }

    /** Marks one of the current user's records as returned. */
    suspend fun markAsReturned(itemId: String): Result<Unit> = runCatching {
        val userId = requireSignedInUserId()

        client.from(TABLE_NAME).update(
            {
                set("is_returned", true)
            }
        ) {
            filter {
                eq("id", itemId)
                eq("user_id", userId)
            }
        }
    }

    private fun requireSignedInUserId(): String {
        return client.auth.currentUserOrNull()?.id
            ?: error("You must be signed in to manage lending records.")
    }

    private companion object {
        const val TABLE_NAME = "borrowed_items"
    }
}
