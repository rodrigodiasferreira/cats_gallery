package br.org.venturus.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.org.venturus.example.database.dao.ImageDAO
import br.org.venturus.example.model.imgur.Image

@Database(
    version = 1,
    entities = [Image::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDAO
}