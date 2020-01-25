package ru.bk.klim9.chat.domain.media

import android.net.Uri
import ru.bk.klim9.chat.domain.interactor.UseCase
import ru.bk.klim9.chat.domain.type.None
import javax.inject.Inject

class CreateImageFile @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<Uri, None>() {

    override suspend fun run(params: None) = mediaRepository.createImageFile()
}