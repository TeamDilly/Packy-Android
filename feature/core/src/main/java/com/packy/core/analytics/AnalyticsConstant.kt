package com.packy.core.analytics

object AnalyticsConstant {

    enum class AnalyticsLabel(
        val label: String
    ){
        VIEW("view")
    }
    enum class PageName(
        override val event: String,
        override val key: String = "PageName"
    ): AnalyticsEvent{
        BOX_ADD_INFO("box_add_info"),
        BOX_CHOICE_BOX("box_choice_box"),
        BOX_DETAIL("box_detail"),
        BOX_ADD_TITLE("box_add_title"),
        BOX_SHARE("box_share"),
        BOX_OPEN_ERROR("box_open_error"),
        BOX_DETAIL_OPEN("box_detail_open")
    }

    data class ContentId(
        override val event: String,
        override val key: String = "contentID",
    ):AnalyticsEvent
}

interface AnalyticsEvent{
    val event: String
    val key: String
}