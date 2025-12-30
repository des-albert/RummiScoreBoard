package org.dba.scoreboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val p1Wins: Int,
    val p2Wins: Int,
    val p3Wins: Int,
    val timestamp: Long = System.currentTimeMillis()
)