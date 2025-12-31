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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dba.scoreboard.PlayerTotals
import org.dba.scoreboard.R
import org.dba.scoreboard.ScoreViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreenContent(
    viewModel: ScoreViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,

) {
    val imageMap = mapOf(
        "DB" to R.drawable.db,
        "Bo" to R.drawable.bo,
        "Steve" to R.drawable.steve
    )
    val scores by viewModel.allScores.collectAsState(initial = emptyList())
    val totals by viewModel.playerTotals.collectAsState(initial = PlayerTotals(0, 0, 0))

    val players = viewModel.players

    val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

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
                text = "History"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Background color
                    contentColor = MaterialTheme.colorScheme.error
                ),
                onClick = {
                    viewModel.saveScores(players[0].wins, players[1].wins, players[2].wins)
                }
            ) {
                Text("Save Results")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        val wins  = listOf(totals.p1Total, totals.p2Total, totals.p3Total)
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
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = wins[index].toString(),
                        fontSize = 36.sp,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Background color
                    contentColor = MaterialTheme.colorScheme.error
                ),
                onClick = onNavigateBack

            ) {
                Text("Home")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ) {
            items(scores) { score ->
                val formattedDate = simpleDateFormat.format(score.timestamp)
                Text(
                    buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            append("$formattedDate")
                        }
                        withStyle(
                            SpanStyle(
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            append("        ${score.p1Wins}     ${score.p2Wins}     ${score.p3Wins}  ")
                        }
                    }
                )
            }
        }




    }
}
