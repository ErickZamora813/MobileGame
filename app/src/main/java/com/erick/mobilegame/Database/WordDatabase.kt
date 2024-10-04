package com.erick.mobilegame.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [WordEntity::class], version = 1)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase(database.wordDao())
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            val words = listOf(
                "coche",
                "perro",
                "gatos",
                "silla",
                "plano",
                "flora",
                "rocas",
                "pesas",
                "aires",
                "aguas"
            )

            val validWords = words.filter { it.length == 5 }
            validWords.forEach { word ->
                println("Insertando palabra válida: $word")
                wordDao.insertAll(WordEntity(word = word))
            }

            println("Total de palabras insertadas: ${validWords.size}")
        }
    }
}

