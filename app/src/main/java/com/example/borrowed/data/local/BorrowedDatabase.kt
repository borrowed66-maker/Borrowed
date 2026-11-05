package com.example.borrowed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.borrowed.data.local.converter.Converters
import com.example.borrowed.data.local.dao.BorrowedItemDao
import com.example.borrowed.data.local.dao.UserDao
import com.example.borrowed.data.local.entity.BorrowedItemEntity
import com.example.borrowed.data.local.entity.UserEntity

@Database(
    entities = [BorrowedItemEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BorrowedDatabase : RoomDatabase() {
    abstract fun borrowedItemDao(): BorrowedItemDao
    abstract fun userDao(): UserDao
}