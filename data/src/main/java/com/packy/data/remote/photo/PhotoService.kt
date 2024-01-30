package com.packy.data.remote.photo

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.packy.data.model.photo.UploadPhotoUrl
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class PhotoService @Inject constructor(
    private val httpClient: HttpClient,
    private val contentResolver: ContentResolver,
    private val context: Context
) {

    suspend fun getUploadUrl(fileName: String): Resource<UploadPhotoUrl> =
        httpClient.get("/api/v1/file/presigned-url/${fileName}.png").toResource()

    @OptIn(InternalAPI::class)
    suspend fun uploadPhoto(
        uploadPhotoUrl: String,
        uri: String
    ) {
        val file = processUri(
            fileName = "test",
            uri = Uri.parse(uri)
        )
        val response = awsHttpClient.put(uploadPhotoUrl) {
            body = customMultiPartMixedDataContent(formData {
                appendInput(
                    key = "upload-image",
                    headers = Headers.build {
                        append(
                            HttpHeaders.ContentLength,
                            file.length().toString()
                        )
                        append(
                            HttpHeaders.ContentType,
                            "image/png"
                        )
                        append(
                            HttpHeaders.ContentLength,
                            file.length()
                        )
                    },
                    size = file.length(),
                    block = {
                        buildPacket {
                            writeFully(file.readBytes())
                        }
                    })
            })
        }
        println("LOGEE $response")
    }

    private fun customMultiPartMixedDataContent(parts: List<PartData>): MultiPartFormDataContent {
        val boundary = "WebAppBoundary"
        val contentType = ContentType.MultiPart.Mixed.withParameter(
            "boundary",
            boundary
        )
        return MultiPartFormDataContent(
            parts,
            boundary,
            contentType
        )
    }

    private fun processUri(fileName: String, uri: Uri): File {
        val filePath = getRealPathFromURI(uri)
        val inputFile = File(filePath)
        val bitmap = BitmapFactory.decodeFile(inputFile.absolutePath)

        // PNG 파일로 저장
        return saveBitmapAsPNG(
            fileName,
            bitmap
        )
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val filePath = cursor?.getString(columnIndex ?: 0) ?: ""
        cursor?.close()
        return filePath
    }

    private fun saveBitmapAsPNG(
        fileName: String,
        bitmap: Bitmap
    ): File {

        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(
            storageDir,
            fileName
        )

        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    outputStream
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    private fun saveImageAsPng(imageUri: String): File? {
        val uri = Uri.parse(imageUri)
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath: String = cursor.getString(columnIndex)

            val originalFile = File(filePath)

            try {
                val originalBitmap = BitmapFactory.decodeFile(filePath)

                val pngFilePath = "${originalFile.parent}/${originalFile.nameWithoutExtension}.png"
                val pngFile = File(pngFilePath)

                FileOutputStream(pngFile).use { out ->
                    originalBitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        out
                    )
                }

                println("LOGEE PNG file path: $pngFilePath")
                return pngFile
            } catch (e: Exception) {
                // 이미지를 읽거나 저장하는 중에 오류가 발생한 경우 예외 처리
                println("LOGEE PNG file path: $e")
                e.printStackTrace()
                return null
            }
        } ?: return null
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