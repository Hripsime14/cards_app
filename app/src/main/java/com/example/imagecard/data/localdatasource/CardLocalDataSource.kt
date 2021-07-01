package com.example.imagecard.data.localdatasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.LiveData
import com.example.imagecard.constants.FIRST_TIME
import com.example.imagecard.data.database.dao.CardDAO
import com.example.imagecard.data.entity.CardEntity
import com.example.imagecard.extention.get
import com.example.imagecard.extention.put


class CardLocalDataSource(private val cardDAO: CardDAO, private val dataStore: DataStore<Preferences>) {

    suspend fun insertCards(cards: List<CardEntity>) = cardDAO.insertCards(cards)

    fun getCards(): LiveData<List<CardEntity>> = cardDAO.getCards()

    suspend fun addCard(card: CardEntity) = cardDAO.addCard(card)

    suspend fun updateCards(card: CardEntity) = cardDAO.updateCards(card)

    fun getFirstTimeFlagFromDataStore() = dataStore.get(preferencesKey(FIRST_TIME), true)

    suspend fun updateFirstTimeFlagInDataStore() = dataStore.put(preferencesKey(FIRST_TIME), false)



}