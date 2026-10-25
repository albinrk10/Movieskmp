package io.albin.kmpmovies.infrastructure.datasources.database

import androidx.room.Room
import androidx.room.RoomDatabase
import io.albin.kmpmovies.infrastructure.datasources.AndroidAppContext

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = checkNotNull(AndroidAppContext.get()) { "AndroidAppContext must be initialized" }
    val dbFile = context.getDatabasePath("kmp_movies.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}
