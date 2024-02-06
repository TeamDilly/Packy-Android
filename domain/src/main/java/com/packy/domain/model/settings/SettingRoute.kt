package com.packy.domain.model.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SettingRoute(
    @SerialName("title") val title: String
) {
    OFFICIAL_SNS("패키 공식 SNS"),
    INQUIRY("1:1 문의하기"),
    SEND_COMMENT("패키에게 의견 보내기"),
    TERMS_OF_USE("이용약관"),
    PRIVACY_POLICY("개인정보처리방침")
}