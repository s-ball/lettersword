package org.s_ball.lettersword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.s_ball.lettersword.data.WordsRepository
import org.s_ball.lettersword.ui.WordsView
import org.s_ball.lettersword.ui.WordsViewModel
import org.s_ball.lettersword.ui.theme.LettersWordTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val words = WordsRepository(
            applicationContext.resources.openRawResource(R.raw.words)
        )
        val model = WordsViewModel(words)
        enableEdgeToEdge()
        setContent {
            LettersWordTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    WordsView(
                        model,
                        modifier = Modifier.Companion.padding(innerPadding)
                    )
                }
            }
        }
    }
}