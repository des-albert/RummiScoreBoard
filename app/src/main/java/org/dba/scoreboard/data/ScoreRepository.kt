package org.dba.scoreboard.data

import javax.inject.Inject

class ScoreRepository @Inject constructor(
    private val scoreDao: ScoreDao
) {
    suspend fun saveScores(
        wins1: Int, wins2: Int, wins3: Int,
        points1: Int, points2: Int, points3: Int
    ) {
        scoreDao.insert(
            ScoreEntry(
                p1Wins = wins1,
                p2Wins = wins2,
                p3Wins = wins3,
                p1Points = points1,
                p2Points = points2,
                p3Points = points3
            )
        )
    }

    fun getAllScores() = scoreDao.getAllScores()
}