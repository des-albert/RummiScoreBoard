package org.dba.scoreboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.dba.scoreboard.data.ScoreEntry
import org.dba.scoreboard.data.ScoreRepository
import javax.inject.Inject

data class PlayerTotals(
    val p1Total: Int,
    val p2Total: Int,
    val p3Total: Int
)

data class Score(
    var name: String,
    var total: Int,
    var wins: Int,
)

data class Result(
    var winner: Int,
    var leftScore: Int,
    var rightScore: Int,
    var numberGames: Int
)
@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val repository: ScoreRepository
) : ViewModel() {
    val allScores: Flow<List<ScoreEntry>> = repository.getAllScores()

    val playerTotals: Flow<PlayerTotals> = allScores.map { scores ->
        PlayerTotals(
            p1Total = scores.sumOf { it.p1Wins },
            p2Total = scores.sumOf { it.p2Wins },
            p3Total = scores.sumOf { it.p3Wins }
        )
    }

    val scores = mutableStateListOf(
        Score("DB", 0, 0),
        Score("Bo", 0, 0),
        Score("Steve", 0, 0)
    )

    var previousResult by mutableStateOf(
        Result(0, 0, 0, 0)
    )

    val players: List<Score> get() = scores

    fun updateScore(index: Int, newScore: Score) {
        scores[index] = newScore
    }

    fun saveScores(player1: Int, player2: Int, player3: Int) {
        viewModelScope.launch {
            repository.saveScores(player1, player2, player3)
        }
    }
}