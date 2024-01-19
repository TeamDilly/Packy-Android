package com.packy.data.usecase.music

import com.packy.data.repository.music.MusicRepositoryImp
import com.packy.domain.model.music.Music
import com.packy.domain.usecase.music.SuggestionMusicUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SuggestionMusicUseCaseImp @Inject constructor(
    private val musicRepository: MusicRepositoryImp
): SuggestionMusicUseCase {
    override suspend fun suggestionMusic(): Flow<Resource<List<Music>>> = musicRepository.suggestionMusic()
}