package com.example.imagecard.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagecard.R
import com.example.imagecard.data.datarepo.CardRepository
import com.example.imagecard.data.entity.CardEntity
import com.example.imagecard.enum.ImageEnum
import com.example.imagecard.utils.getRandomNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardViewModel(private val repository: CardRepository): ViewModel() {


    fun insertCards() {
        viewModelScope.launch(Dispatchers.IO) {
            getFirstTimeFlagFromDataStore().collect {
                if (it) {
                    val entityList = getEntityList()
                    repository.insertCards(entityList)
                    updateFirstTimeFlagInDataStore()
                }
            }
        }
    }

    private fun getEntityList() : List<CardEntity> {
        val list = mutableListOf<CardEntity>()
        for (i in 1 .. 200) {
            val random  = getRandomNumber(3)
            val card = generateCard(random, i)
            list.add(card)
        }
        return list
    }

    private fun generateCard(random: Int, index: Int) = when (random) {
        ImageEnum.Cactus.ordinal -> CardEntity(imagePath = R.drawable.cactus.toString(), title = "Title $index")
        ImageEnum.Spade.ordinal -> CardEntity(imagePath = R.drawable.spade.toString(), title = "Title $index")
        else -> CardEntity(imagePath = R.drawable.tree.toString(),title =  "Title $index")
    }

    fun addCard(index: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val random  = getRandomNumber(3)
                val card = generateCard(random, index)
                repository.addCard(card)
            }
        }
    }

    fun getCards(): LiveData<List<CardEntity>> = repository.getCards()


    fun updateCards(card: CardEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    repository.updateCards(card)
                } catch (t: Throwable) {
                    Log.d("CardViewModel", "updateCards: ${t.message}")
                }
            }
        }
    }



    private fun getFirstTimeFlagFromDataStore(): Flow<Boolean> = repository.getFirstTimeFlagFromDataStore()

    private fun updateFirstTimeFlagInDataStore() = viewModelScope.launch {
        repository.updateFirstTimeFlagInDataStore()
    }
}