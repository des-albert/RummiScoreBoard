package org.dba.scoreboard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dba.scoreboard.R
import org.dba.scoreboard.Score
import org.dba.scoreboard.ScoreViewModel


@Composable
fun HomeScreenContent(
    viewModel: ScoreViewModel,
    onNavigateToDetails: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val imageMap = mapOf(
        "DB" to R.drawable.db,
        "Bo" to R.drawable.bo,
        "Steve" to R.drawable.steve
    )
    var inputVisible by remember { mutableStateOf(false) }
    val players = viewModel.players
    var winner by remember { mutableIntStateOf(0) }
    var placeLeft by remember { mutableStateOf("") }
    var placeRight by remember { mutableStateOf("") }
    var lossLeft by remember { mutableStateOf("") }
    var lossRight by remember { mutableStateOf("") }
    val prevScore = viewModel.previousResult

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "Home"
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        if (prevScore.numberGames > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = " ${prevScore.numberGames} games played"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for ((index, score) in players.withIndex()) {
                Column(
                    modifier = modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val resourceId = imageMap[score.name]
                    if (resourceId != null) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                        {
                            Image(
                                painter = painterResource(id = resourceId),
                                contentDescription = null,
                                modifier = Modifier.size(72.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        ElevatedButton(
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer, // Background color
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            onClick = {
                                inputVisible = true
                                winner = index
                                when (index) {
                                    0 -> {
                                        placeLeft = players[1].name
                                        placeRight = players[2].name
                                    }

                                    1 -> {
                                        placeLeft = players[0].name
                                        placeRight = players[2].name
                                    }

                                    2 -> {
                                        placeLeft = players[0].name
                                        placeRight = players[1].name
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = score.name,
                                fontSize = 24.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = score.total.toString(),
                            fontSize = 36.sp,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = score.wins.toString(),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.errorContainer
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        //  Game Result input

        if (inputVisible) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "${players[winner].name} won",
                    fontSize = 24.sp,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = lossLeft,
                        onValueChange = {
                            lossLeft = it
                        },
                        placeholder = {
                            Text(
                                text = placeLeft
                            )
                        },
                        singleLine = true,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.background,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedTextColor = MaterialTheme.colorScheme.error
                        ),
                    )
                }
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = lossRight,
                        onValueChange = {
                            lossRight = it
                        },
                        placeholder = {
                            Text(
                                text = placeRight
                            )
                        },
                        singleLine = true,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.background,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            focusedTextColor = MaterialTheme.colorScheme.error
                        ),
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ElevatedButton(
                    onClick = {
                        inputVisible = false
                        var newScore: Score
                        when (winner) {
                            0 -> {
                                newScore = Score(
                                    "DB",
                                    players[0].total + lossLeft.toInt() + lossRight.toInt(),
                                    players[0].wins + 1
                                )
                                viewModel.updateScore(0, newScore)

                                newScore = Score(
                                    "Bo",
                                    players[1].total - lossLeft.toInt(),
                                    players[1].wins
                                )
                                viewModel.updateScore(1, newScore)

                                newScore = Score(
                                    "Steve",
                                    players[2].total - lossRight.toInt(),
                                    players[2].wins
                                )
                                viewModel.updateScore(2, newScore)

                            }

                            1 -> {
                                newScore = Score(
                                    "DB",
                                    players[0].total - lossLeft.toInt(),
                                    players[0].wins
                                )
                                viewModel.updateScore(0, newScore)

                                newScore = Score(
                                    "Bo",
                                    players[1].total + lossLeft.toInt() + lossRight.toInt(),
                                    players[1].wins + 1
                                )
                                viewModel.updateScore(1, newScore)

                                newScore = Score(
                                    "Steve",
                                    players[2].total - lossRight.toInt(),
                                    players[2].wins
                                )
                                viewModel.updateScore(2, newScore)
                            }

                            2 -> {
                                newScore = Score(
                                    "DB",
                                    players[0].total - lossLeft.toInt(),
                                    players[0].wins
                                )
                                viewModel.updateScore(0, newScore)

                                newScore = Score(
                                    "Bo",
                                    players[1].total - lossRight.toInt(),
                                    players[1].wins
                                )
                                viewModel.updateScore(1, newScore)

                                newScore = Score(
                                    "Steve",
                                    players[2].total + lossLeft.toInt() + lossRight.toInt(),
                                    players[2].wins + 1
                                )
                                viewModel.updateScore(2, newScore)
                            }
                        }

                        prevScore.winner = winner
                        prevScore.leftScore = lossLeft.toInt()
                        prevScore.rightScore = lossRight.toInt()
                        prevScore.numberGames += 1

                        lossLeft = ""
                        lossRight = ""
                    }

                ) {
                    Text(
                        text = "Save Scores",
                        fontSize = 18.sp
                    )
                }
            }
        }

        if (!inputVisible) {

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ElevatedButton(
                    onClick = {
                        var newScore: Score
                        when (prevScore.winner) {
                            0 -> {
                                prevScore.numberGames -= 1
                                prevScore.winner = 3
                                newScore = Score(
                                    "DB",
                                    players[0].total - prevScore.leftScore - prevScore.rightScore,
                                    players[0].wins - 1
                                )
                                viewModel.updateScore(0, newScore)

                                newScore = Score(
                                    "Bo",
                                    players[1].total + prevScore.leftScore,
                                    players[1].wins
                                )
                                viewModel.updateScore(1, newScore)

                                newScore = Score(
                                    "Steve",
                                    players[2].total + prevScore.rightScore,
                                    players[2].wins
                                )
                                viewModel.updateScore(2, newScore)
                            }

                            1 -> {
                                prevScore.numberGames -= 1
                                prevScore.winner = 3
                                newScore = Score(
                                    "DB",
                                    players[0].total + prevScore.leftScore,
                                    players[0].wins
                                )
                                viewModel.updateScore(0, newScore)

                                newScore = Score(
                                    "Bo",
                                    players[1].total - prevScore.leftScore - prevScore.rightScore,
                                    players[1].wins - 1
                                )
                                viewModel.updateScore(1, newScore)

                                newScore = Score(
                                    "Steve",
                                    players[2].total + prevScore.rightScore,
                                    players[2].wins
                                )
                                viewModel.updateScore(2, newScore)
                            }
                            2 -> {
                                prevScore.numberGames -= 1
                                prevScore.winner = 3
                                newScore = Score(
                                    "DB",
                                    players[0].total + prevScore.leftScore,
                                    players[0].wins
                                )
                                viewModel.updateScore(0, newScore)

                                newScore = Score(
                                    "Bo",
                                    players[1].total + prevScore.rightScore,
                                    players[1].wins
                                )
                                viewModel.updateScore(1, newScore)

                                newScore = Score(
                                    "Steve",
                                    players[2].total - prevScore.leftScore - prevScore.rightScore,
                                    players[2].wins - 1
                                )
                                viewModel.updateScore(2, newScore)
                            }
                            3 -> {

                            }
                        }
                    }
                ) {
                    Text(
                        text = "Undo",
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Background color
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = onNavigateToDetails
                ) {
                    Text("Go to History")
                }
            }
        }
    }
}

