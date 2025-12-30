package org.dba.scoreboard.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScoreEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}