package com.example.imagecard.data.datarepo

import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.LiveData
import com.example.imagecard.constants.FIRST_TIME
import com.example.imagecard.data.entity.CardEntity
import com.example.imagecard.data.localdatasource.CardLocalDataSource
import com.example.imagecard.extention.get
import com.example.imagecard.extention.put


class CardRepository(private val localDataSource: CardLocalDataSource) {

    suspend fun insertCards(cards: List<CardEntity>) = localDataSource.insertCards(cards)

    fun getCards(): LiveData<List<CardEntity>> = localDataSource.getCards()

    suspend fun addCard(card: CardEntity) = localDataSource.addCard(card)

    suspend fun updateCards(card: CardEntity) = localDataSource.updateCards(card)

    fun getFirstTimeFlagFromDataStore() = localDataSource.getFirstTimeFlagFromDataStore()

    suspend fun updateFirstTimeFlagInDataStore() = localDataSource.updateFirstTimeFlagInDataStore()



}