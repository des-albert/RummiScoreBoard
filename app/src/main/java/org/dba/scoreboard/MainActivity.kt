package org.dba.scoreboard

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.dba.scoreboard.ui.screens.HistoryScreenContent
import org.dba.scoreboard.ui.screens.HomeScreenContent
import org.dba.scoreboard.ui.theme.ScoreBoardTheme


@HiltAndroidApp
class ScoreBoardApplication : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScoreBoardTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                ) { innerPadding ->
                    NavigationApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "ScoreBoard"
            )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.secondary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary,
            subtitleContentColor = MaterialTheme.colorScheme.secondary
        )
    )
}


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object History : Screen("history")
}

object Graph {
    const val ROOT = "root_graph"
}

@Composable
fun NavigationApp(
    modifier: Modifier = Modifier

) {
    val navController = rememberNavController()

    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            route = Graph.ROOT

        ) {
            composable(route = Screen.Home.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Graph.ROOT)
                }
                val sharedViewModel: ScoreViewModel = hiltViewModel(parentEntry)

                HomeScreenContent(
                    sharedViewModel,
                    onNavigateToDetails = {
                        navController.navigate(Screen.History.route)
                    }
                )
            }

            composable(route = Screen.History.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Graph.ROOT)
                }
                val sharedViewModel: ScoreViewModel = hiltViewModel(parentEntry)
                HistoryScreenContent(
                    sharedViewModel,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ScoreBoardPreview() {
    ScoreBoardTheme {
        Scaffold(
            topBar = {
                TopBar()
            },
            modifier = Modifier.fillMaxHeight()
        ) { innerPadding ->
            NavigationApp(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}