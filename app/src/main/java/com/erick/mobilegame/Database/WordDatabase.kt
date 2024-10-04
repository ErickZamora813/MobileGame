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
                "abeto",
                "actor",
                "aguas",
                "agudo",
                "alado",
                "albas",
                "altar",
                "Anton",
                "atizo",
                "avala",
                "avion",
                "azul",
                "babas",
                "bacas",
                "bache",
                "bajes",
                "balas",
                "bebes",
                "belen",
                "bicho",
                "bizco",
                "bueno",
                "busca",
                "cabra",
                "cafes",
                "cajas",
                "calar",
                "calas",
                "calca",
                "calla",
                "calma",
                "camba",
                "campo",
                "canas",
                "cantos",
                "capto",
                "caras",
                "carro",
                "casas",
                "catar",
                "caida",
                "cejas",
                "cenas",
                "cepas",
                "cerca",
                "cerco",
                "cerdo",
                "chile",
                "china",
                "ciego",
                "cines",
                "citas",
                "clara",
                "clavo",
                "colas",
                "colon",
                "coral",
                "coras",
                "corea",
                "corro",
                "cosas",
                "costo",
                "crudo",
                "curar",
                "dados",
                "dagas",
                "datos",
                "danos",
                "dejar",
                "dejes",
                "denso",
                "dices",
                "divos",
                "dotes",
                "dunas",
                "dures",
                "duros",
                "echas",
                "edito",
                "elevo",
                "emule",
                "emulo",
                "enoje",
                "error",
                "fallo",
                "falto",
                "feria",
                "fetos",
                "fijos",
                "filas",
                "filia",
                "finca",
                "gafas",
                "galas",
                "galos",
                "ganas",
                "gases",
                "gasto",
                "giras",
                "gordo",
                "gorro",
                "grave",
                "grito",
                "heces",
                "hielo",
                "malos",
                "mania",
                "marca",
                "melon",
                "menos",
                "meter",
                "metro",
                "moler",
                "monte",
                "morir",
                "nacer",
                "nadar",
                "narro",
                "natas",
                "naves",
                "necio",
                "ninos",
                "notas",
                "nubes",
                "obras",
                "ocios",
                "ollas",
                "ondas",
                "onzas",
                "opera",
                "otros",
                "palas",
                "Paris",
                "pedir",
                "pelea",
                "pelos",
                "peras",
                "perro",
                "pesos",
                "pilas",
                "pinto",
                "poder",
                "reloj",
                "rubio",
                "rasco",
                "ratas",
                "ratos",
                "redes",
                "remar",
                "renos",
                "renta",
                "sabio",
                "sacar",
                "salir",
                "selva",
                "sanar",
                "sopas",
                "secar",
                "serio",
                "situo",
                "sobar",
                "sonar",
                "subir",
                "sucio",
                "siete",
                "tabla",
                "tacos",
                "Tania",
                "tapas",
                "tazas",
                "telon",
                "tener",
                "tenis",
                "terco",
                "terso",
                "Texas",
                "tipos",
                "tiras",
                "tomar",
                "Tomas",
                "tonos",
                "tonto",
                "toque",
                "torpe",
                "trote",
                "vacas",
                "vagos",
                "valer",
                "valor",
                "vedas",
                "velas",
                "vemos",
                "venas",
                "venir",
                "verde",
                "viera",
                "vigas",
                "vinos",
                "vivir",
                "volar",
                "votar",
                "yates",
                "yemas",
                "Yemen",
                "yendo",
                "yenes",
                "yesca",
                "yogur",
                "yugos",
                "zonas",
                "zorro",
                "zurdo"
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

