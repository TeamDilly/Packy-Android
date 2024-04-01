package com.packy.core.analytics

object AnalyticsConstant {

    enum class AnalyticsLabel(
        val label: String
    ) {
        VIEW("View"),
        CLICK("Click")
    }

    enum class PageName(
        override val event: String,
        override val key: String = "PageName"
    ) : AnalyticsEvent {
        BOX_ADD_INFO("box_add_info"),
        BOX_CHOICE_BOX("box_choice_box"),
        BOX_DETAIL("box_detail"),
        BOX_ADD_TITLE("box_add_title"),
        BOX_SHARE("box_share"),
        BOX_OPEN_ERROR("box_open_error"),
        BOX_DETAIL_OPEN("box_detail_open")
    }

    enum class ComponentName(
        override val event: String,
        override val key: String = "ComponentName"
    ): AnalyticsEvent{
        BOX_DETAIL_DONE_BUTTON("box_detail_done_button")
    }

    data class ContentId(
        override val event: String,
        override val key: String = "ContentId",
    ) : AnalyticsEvent

    data class EmptyItems(
        val emptyItems: List<EmptyItem>,
        override val event: String = emptyItems.toString(),
        override val key: String = "EmptyItems"
    ) : AnalyticsEvent

    enum class EmptyItem {
        PHOTO,
        STICKER1,
        STICKER2,
        LETTER,
        MUSIC,
        GIFT
    }
}

interface AnalyticsEvent {
    val event: String
    val key: String
}