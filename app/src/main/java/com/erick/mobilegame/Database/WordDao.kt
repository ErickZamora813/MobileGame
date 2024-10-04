package com.erick.mobilegame.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(): WordEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg words: WordEntity)
}