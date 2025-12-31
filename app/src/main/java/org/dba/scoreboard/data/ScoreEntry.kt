package org.dba.scoreboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class ScoreEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val p1Wins: Int,
    val p2Wins: Int,
    val p3Wins: Int,
    val p1Points: Int,
    val p2Points: Int,
    val p3Points: Int,
    val timestamp: Long = System.currentTimeMillis()
)