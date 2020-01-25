package ru.bk.klim9.chat.domain.media

import android.graphics.Bitmap
import android.net.Uri
import ru.bk.klim9.chat.domain.interactor.UseCase
import javax.inject.Inject

class GetPickedImage @Inject constructor(
    private val mediaRepository: MediaRepository
) : UseCase<Bitmap, Uri?>() {

    override suspend fun run(params: Uri?) = mediaRepository.getPickedImage(params)
}