package com.packy.common.kakaoshare

import android.content.ActivityNotFoundException
import android.content.Context
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.packy.lib.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoShare @Inject constructor() {

    @OptIn(InternalCoroutinesApi::class)
    suspend fun sendGiftBox(
        kakaoCustomFeed: KakaoCustomFeed,
        context: Context
    ): Resource<Unit> = suspendCancellableCoroutine { conrinuation ->
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareCustom(
                context = context,
                templateId = CUSTOM_FEED_ID,
                templateArgs = kakaoCustomFeed.toFeedArgs()
            ) { sharingResult, error ->
                if (error != null) {
                    conrinuation.tryResume(Resource.NetworkError(error))
                } else if (sharingResult != null) {
                    conrinuation.tryResume(
                        Resource.Success(
                            data = Unit,
                            message = "카카오톡 공유 성공",
                            code = "200"
                        )
                    )
                }
                conrinuation.cancel()
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeCustomUrl(
                templateId = CUSTOM_FEED_ID,
                templateArgs = kakaoCustomFeed.toFeedArgs()
            )

            try {
                KakaoCustomTabsClient.openWithDefault(
                    context,
                    sharerUrl
                )
                conrinuation.tryResume(
                    Resource.Success(
                        data = Unit,
                        message = "카카오톡 공유 성공",
                        code = "200"
                    )
                )
            } catch (e: UnsupportedOperationException) {
                conrinuation.tryResume(Resource.NetworkError(e))
                conrinuation.cancel()
            }

            try {
                KakaoCustomTabsClient.open(
                    context,
                    sharerUrl
                )
            } catch (e: ActivityNotFoundException) {
                conrinuation.tryResume(Resource.NetworkError(e))
                conrinuation.cancel()
            }
            conrinuation.cancel()
        }
    }

    companion object {
        const val CUSTOM_FEED_ID = 103895L
    }
}