package ru.bk.klim9.chat.domain.media

import android.graphics.Bitmap
import ru.bk.klim9.chat.domain.interactor.UseCase
import javax.inject.Inject

class EncodeImageBitmap @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<String, Bitmap?>() {

    override suspend fun run(params: Bitmap?) = mediaRepository.encodeImageBitmap(params)
}