/*
 * Copyright (c)  s-ball 2025-current - MIT License
 */

package org.s_ball.lettersword

import org.s_ball.lettersword.data.IWordsRepository

object RepoHolder {
    private var repo: IWordsRepository? = null

    fun initialize(repository: IWordsRepository) {
        if (repo == null) {
            repo = repository
        }
    }

    fun getRepo(): IWordsRepository {
        return repo!!
    }
}