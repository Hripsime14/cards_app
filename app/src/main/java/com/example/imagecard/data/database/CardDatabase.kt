package com.example.imagecard.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagecard.data.database.dao.CardDAO
import com.example.imagecard.data.entity.CardEntity

@Database(entities = [CardEntity::class], version = 1, exportSchema = false)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDAO
}