package org.dba.scoreboard.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(scoreEntry: ScoreEntry)

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getAllScores(): Flow<List<ScoreEntry>>
}