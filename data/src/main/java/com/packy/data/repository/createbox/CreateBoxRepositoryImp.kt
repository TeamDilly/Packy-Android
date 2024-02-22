package com.packy.data.repository.createbox

import com.packy.data.local.AccountPrefManager
import com.packy.data.model.createbox.box.CreateBoxRequest
import com.packy.data.remote.box.BoxService
import com.packy.domain.model.box.CreatedBox
import com.packy.domain.model.createbox.box.CreateBox
import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.repository.photo.PhotoRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class CreateBoxRepositoryImp @Inject constructor(
    private val prefManager: AccountPrefManager,
    private val photoRepository: PhotoRepository,
    private val boxService: BoxService
) : CreateBoxRepository {
    override suspend fun shouldShowBoxMotion(): Flow<Boolean> =
        prefManager.shouldShowBoxMotion.getData()

    override suspend fun shownShowBoxMotion() {
        prefManager.shouldShowBoxMotion.putData(false)
    }

    override suspend fun shouldShowBoxTutorial(): Flow<Boolean> =
        prefManager.shouldShowBoxTutorial.getData()

    override suspend fun shownShowBoxTutorial() {
        prefManager.shouldShowBoxTutorial.putData(false)
    }

    override suspend fun shouldShowBoxSharMotion(): Flow<Boolean> =
        prefManager.shouldShowBoxShareMotion.getData()

    override suspend fun shownShowBoxSharMotion() {
        prefManager.shouldShowBoxShareMotion.putData(false)
    }

    override suspend fun setCreateBox(createBox: CreateBox) {
        prefManager.createBox.putData(createBox)
    }

    override suspend fun getCreatedBox(): CreateBox = prefManager.createBox.getData().first()
    override suspend fun createBox(createBox: CreateBox): Resource<CreatedBox> =
        withContext(Dispatchers.IO) {
            val uploadPhotoDeferred = async(Dispatchers.IO) {
                createBox.photo?.let {
                    photoRepository.uploadFile(
                        fileName = UUID.randomUUID().toString(),
                        uri = it.photoUrl
                    )
                }
            }

            val uploadGiftDeferred = async(Dispatchers.IO) {
                createBox.gift?.url?.let { url ->
                    photoRepository.uploadFile(
                        fileName = UUID.randomUUID().toString(),
                        uri = url
                    )
                } ?: Resource.Success(
                    "gift should be null",
                    "gift should be null",
                    "200"
                )
            }

            val uploadPhotoUrl = uploadPhotoDeferred.await()
            val giftUrl = uploadGiftDeferred.await()
            if (uploadPhotoUrl !is Resource.Success || giftUrl !is Resource.Success) {
                return@withContext Resource.ApiError(
                    data = null,
                    message = "Failed to upload photo or gift",
                    code = "400"
                )
            }
            val createBoxRequest = CreateBoxRequest.formEntity(
                createBox = createBox,
                photoUrl = uploadPhotoUrl.data,
                giftUrl = if (createBox.gift == null) null else giftUrl.data
            ) ?: run {
                return@withContext Resource.ApiError(
                    data = null,
                    message = "Failed to create box",
                    code = "400"
                )
            }

            val createBoxDto = boxService.createBox(createBoxRequest)
            return@withContext createBoxDto.map {
                CreatedBox(
                    id = it.id.toString(),
                )
            }
        }

    override suspend fun getKakaoMessageImage(giftBoxId: Long): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val kakaoMessageImage = boxService.getKakaoMessageImage(giftBoxId)
        emit(kakaoMessageImage)
    }

}