package com.packy.common.kakaoshare

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

    fun sendGiftBox(
        kakaoCustomFeed: KakaoCustomFeed,
        context: Context,
        sharedCallBack: (Resource<String?>) -> Unit,
    ){
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareCustom(
                context = context,
                templateId = CUSTOM_FEED_ID,
                templateArgs = kakaoCustomFeed.toFeedArgs(),
                serverCallbackArgs = null
            ) { sharingResult, error ->
                if (error != null) {
                    sharedCallBack(Resource.NetworkError(error))
                } else if (sharingResult != null) {
                    context.startActivity(sharingResult.intent)
                    sharedCallBack(
                        Resource.Success(
                            data = sharingResult.intent.action,
                            message = "카카오톡 공유 성공",
                            code = "200"
                        )
                    )
                }
            }
        } else {
            sharedCallBack(Resource.NetworkError(Exception("카카오톡이 없음")))
        }
    }

    companion object {
        const val CUSTOM_FEED_ID = 103895L
    }
}