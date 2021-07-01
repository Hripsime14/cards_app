package com.example.imagecard.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.imagecard.constants.DATABASE_NAME
import com.example.imagecard.data.entity.CardEntity

@Dao
interface CardDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Query("SELECT * FROM $DATABASE_NAME")
    fun getCards(): LiveData<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: CardEntity)

    @Update
    suspend fun updateCards(card: CardEntity)

}