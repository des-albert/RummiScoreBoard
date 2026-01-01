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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val players = viewModel.players
    val imageMap = mapOf(
        players[0].name to R.drawable.player0,
        players[1].name to R.drawable.player1,
        players[2].name to R.drawable.player2
    )
    val scores by viewModel.allScores.collectAsState(initial = emptyList())
    val totals by viewModel.playerTotals.collectAsState(initial = PlayerTotals(0, 0, 0, 0, 0, 0))

    val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    val headStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center )
    val winsStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.secondaryContainer )
    val pointsStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.secondaryContainer )

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
                    containerColor = MaterialTheme.colorScheme.secondary, // Background color
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onNavigateBack

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Arrow"
                )
                Spacer(Modifier.width(8.dp))
                Text("Home")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val wins = listOf(totals.w1Total, totals.w2Total, totals.w3Total)
        val points = listOf(totals.p1Total, totals.p2Total, totals.p3Total)
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = points[index].toString(),
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.errorContainer
                    )
                }
            }

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
                    viewModel.saveScores(players[0].wins, players[1].wins, players[2].wins,
                        players[0].points, players[1].points, players[2].points)
                }
            ) {
                Text("Save Results")
            }
        }


        // Score History
        Spacer(modifier = Modifier.height(24.dp))

        if (scores.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)   // bottom half of the screen
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Date",
                                style = headStyle,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = players[0].name,
                                modifier = Modifier.weight(0.4f),
                                style = headStyle
                            )
                            Text(
                                text = players[1].name,
                                modifier = Modifier.weight(0.4f),
                                style = headStyle
                            )
                            Text(
                                text = players[2].name,
                                modifier = Modifier.weight(0.4f),
                                style = headStyle
                            )
                            Text(
                                text = players[0].name,
                                modifier = Modifier.weight(0.4f),
                                style = headStyle
                            )
                            Text(
                                text = players[1].name,
                                modifier = Modifier.weight(0.4f),
                                style = headStyle
                            )
                            Text(
                                text = players[2].name,
                                modifier = Modifier.weight(0.4f),
                                style = headStyle
                            )
                        }
                    }

                    items(scores) { score ->
                        val formattedDate = simpleDateFormat.format(score.timestamp)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Date column
                            Text(
                                text = formattedDate,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.weight(1f)
                            )

                            // Wins columns
                            Text(
                                text = score.p1Wins.toString(),
                                style = winsStyle,
                                modifier = Modifier.weight(0.4f)
                            )

                            Text(
                                text = score.p2Wins.toString(),
                                style = winsStyle,
                                modifier = Modifier.weight(0.4f)
                            )

                            Text(
                                text = score.p3Wins.toString(),
                                style = winsStyle,
                                modifier = Modifier.weight(0.4f)
                            )
                            Text(
                                text = score.p1Points.toString(),
                                style = pointsStyle,
                                modifier = Modifier.weight(0.4f)
                            )

                            Text(
                                text = score.p2Points.toString(),
                                style = pointsStyle,
                                modifier = Modifier.weight(0.4f)
                            )

                            Text(
                                text = score.p3Points.toString(),
                                style = pointsStyle,
                                modifier = Modifier.weight(0.4f),
                            )
                        }
                    }
                }
            }
        }
    }
}
