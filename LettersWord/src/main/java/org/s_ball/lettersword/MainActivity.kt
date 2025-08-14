package org.s_ball.lettersword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.s_ball.lettersword.data.WordsRepository
import org.s_ball.lettersword.ui.NotFrenchDialog
import org.s_ball.lettersword.ui.WordsView
import org.s_ball.lettersword.ui.WordsViewModel
import org.s_ball.lettersword.ui.theme.LettersWordTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locale: java.util.Locale = java.util.Locale.getDefault()
        RepoHolder.initialize(
            WordsRepository(
                applicationContext.resources.openRawResource(R.raw.words)
            )
        )
        enableEdgeToEdge()
        setContent {
            LettersWordTheme {
                var display by rememberSaveable { mutableStateOf(
                    !locale.toString().startsWith("fr")) }
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    if (display) {
                        NotFrenchDialog (
                            onClose = { display = false }
                        ) {
                            this.finish()
                            exitProcess(0)
                        }
                    }
                    else {
                        WordsView(
                            modifier = Modifier.Companion.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}