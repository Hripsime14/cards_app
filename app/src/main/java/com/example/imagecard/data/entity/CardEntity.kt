package com.example.imagecard.data.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imagecard.constants.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var imagePath: String,
    var title: String
)