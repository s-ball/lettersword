/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword

import android.content.Context
import androidx.startup.Initializer
import org.s_ball.lettersword.data.WordsRepository
import org.s_ball.lettersword.ui.WordsViewModel

class RepoInitializer: Initializer<WordsRepository> {
    override fun create(context: Context): WordsRepository {
        val stream = context.resources.openRawResource(R.raw.words)
        val repo = WordsRepository(stream)
        WordsViewModel.Factory.initialize(repo)
        return repo
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}