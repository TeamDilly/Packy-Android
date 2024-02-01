package com.packy.data.remote.photo

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.packy.common.authenticator.ext.removeQueryParameters
import com.packy.data.model.photo.UploadPhotoUrl
import com.packy.lib.utils.Resource
import com.packy.lib.utils.Resource.Success
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.request
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class PhotoService @Inject constructor(
    private val httpClient: HttpClient,
    private val contentResolver: ContentResolver,
    private val context: Context
) {

    suspend fun getUploadUrl(fileName: String): Resource<UploadPhotoUrl> =
        httpClient.get("/api/v1/file/presigned-url/${fileName}.jpg").toResource()

    @OptIn(InternalAPI::class)
    suspend fun uploadPhoto(
        fileName: String,
        uploadPhotoUrl: String,
        uri: String
    ): Resource<String> = withContext(Dispatchers.IO) {
        contentResolver.openInputStream(Uri.parse(uri))?.use { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val tempFile = File(
                context.cacheDir,
                "${fileName}.jpg"
            )
            FileOutputStream(tempFile).use { stream ->
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    stream
                )
                val response = awsHttpClient.put(uploadPhotoUrl) {
                    body = tempFile.readBytes()
                    headers.append(
                        "Content-Type",
                        "application/octet-stream"
                    )
                }
                if (response.status.value == 200) {
                    val imageUrl = response.request.url.toString().removeQueryParameters()
                    return@withContext Success(imageUrl, code = "200", message = "Success Image Upload")
                }else{
                    return@withContext Resource.NetworkError(Exception("Image Upload Error"))
                }
            }
        }
        return@withContext Resource.NetworkError(Exception("Network Error"))
    }


    private val awsHttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i(
                        "Ktor",
                        message
                    )
                }
            }
            level = LogLevel.ALL
        }
    }
}
