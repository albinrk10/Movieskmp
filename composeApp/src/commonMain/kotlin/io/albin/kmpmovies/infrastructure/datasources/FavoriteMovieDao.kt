package io.albin.kmpmovies.infrastructure.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.albin.kmpmovies.infrastructure.models.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Query("SELECT * FROM favorite_movies ORDER BY createdAt DESC")
    fun getAll(): Flow<List<FavoriteMovieEntity>>
}
