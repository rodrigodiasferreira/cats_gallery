package br.org.venturus.example.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.org.venturus.example.model.imgur.Image

@Dao
interface ImageDAO {

    @Query("SELECT * FROM images")
    fun retrieveAll(): LiveData<List<Image>>

    @Query("SELECT * FROM images")
    fun retrieveAllSychronously(): List<Image>

    // Using the REPLACE strategy since the db is used to cache (not critical to overwrite),
    // but I don't see at quick analysis any reason to have conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(images: List<Image>)

    @Query("DELETE FROM images")
    fun deleteAllImages()

}
