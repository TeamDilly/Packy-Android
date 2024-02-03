package com.packy.core.values

object Strings {
    // common
    const val START = "시작하기"
    const val NEXT = "다음"
    const val SKIP = "건너뛰기"
    const val SAVE = "저장"
    const val CONFIRM = "확인"
    const val COMPLETE = "완성"
    const val INPUT_MAX_LENGTH_12 = "12자 이내로 입력해주세요"
    const val OPEN = "열어보기"
    const val CONTACT_US = "문의하기"

    // onboarding
    const val ONBOARDING_TITLE_1 = "패키와 함께 특별한 선물박스를 만들어보세요"
    const val ONBOARDING_TITLE_2 = "다가오는 이벤트를 기다리고\n지나간 이벤트를 추억해요"

    // login
    const val LOGIN_TITLE = "마음으로 채우는 특별한 선물박스"

    // signupNickName
    const val SIGNUP_NICK_NAME_TITLE = "패키에서 사용할\n닉네임을 입력해주세요"
    const val SIGNUP_NICK_NAME_DESCRIPTION = "친구들이 나를 잘 알아볼 수 있는 닉네임으로 설정해주세요"
    const val SIGNUP_NICK_NAME_MAX_VALUE = "6자 이내로 입력해주세요"

    // signupProfile
    const val SIGNUP_PROFILE = "프로필을 선택해주세요"

    // termsAgreement
    const val TERMS_AGREEMENT_TITLE = "서비스 사용을 위한\n약관에 동의해주세요"
    const val TERMS_AGREEMENT_ALL_ALLOW = "전체 동의하기"
    const val TERMS_AGREEMENT_SERVICE_ALLOW = "(필수) 서비스 이용약관"
    const val TERMS_AGREEMENT_PERSONAL_ALLOW = "(필수) 개인정보 수집 및 이용안내"
    const val TERMS_AGREEMENT_NOTIFICATION_ALLOW = "(선택) 이벤트 및 마케팅 수신 동의"

    // box add info
    const val BOX_ADD_INFO_TITLE = "패키와 함께 마음을 담은\n선물박스를 만들어볼까요?"
    const val BOX_ADD_INFO_DESCRIPTION = "보내는 사람과 받는 사람의 이름을 적어주세요"
    const val BOX_ADD_INFO_SENDER = "From."
    const val BOX_ADD_INFO_RECEIVER = "To."
    const val BOX_ADD_INFO_PLACEHOLDER = "6자까지 입력할 수 있어요"

    // box choice
    const val BOX_CHOICE_TITLE = "마음에 드는 선물박스를\n골라주세요"

    // box guide
    const val BOX_GUIDE_PHOTO = "추억 사진 담기"
    const val BOX_GUIDE_Letter = "편지 쓰기"
    const val BOX_GUIDE_MUSIC = "음악 추가하기"
    const val BOX_GUIDE_GIFT = "선물 담기"
    const val BOX_GUIDE_CHANGE_BOX = "박스 다시 고르기"

    // crateBox
    const val CRATE_BOX_MUSIC = "음악 추가하기"
    const val CHOOSE_YOUR_MUSIC_TITLE = "직접 음악 선택하기"
    const val CHOOSE_YOUR_MUSIC_DESCRIPTION = "유튜브 링크로 음악을 넣어주세요"
    const val CHOOSE_PACKY_MUSIC_TITLE = "패키의 음악으로 담기"
    const val CHOOSE_PACKY_MUSIC_DESCRIPTION = "다양한 테마의 음악들을 준비했어요!"

    const val CREATE_BOX_ADD_YOUR_MUSIC_TITLE = "들려주고 싶은 음악"
    const val CREATE_BOX_ADD_YOUR_MUSIC_DESCRIPTION = "유튜브 영상 url을 넣어주세요"
    const val CREATE_BOX_ADD_MUSIC_PLACE_HOLDER = "링크를 붙여주세요"
    const val CREATE_BOX_ADD_MUSIC_FAIL_URL = "올바른 url을 입력해주세요"

    const val CREATE_BOX_ADD_PACKY_MUSIC_TITLE = "패키가 준비한 음악"
    const val CREATE_BOX_ADD_PACKY_MUSIC_DESCRIPTION = "음악을 선택해주세요"

    const val CREATE_BOX_ADD_PHOTO_TITLE = "추억을 담은 사진"
    const val CREATE_BOX_ADD_PHOTO_DESCRIPTION = "추억을 담은 사진"
    const val CREATE_BOX_ADD_PHOTO_PLACEHOLDER = "사진 속 추억을 적어주세요"

    const val CREATE_BOX_ADD_LETTER_TITLE = "편지쓰기"
    const val CREATE_BOX_ADD_LETTER_DESCRIPTION = "마음을 담은 편지를 써보아요"
    const val CREATE_BOX_ADD_LETTER_PLACEHOLDER =
        "편지에 어떤 마음을 담아볼까요?\n따뜻한 인사, 잊지 못할 추억, 고마웠던 순간까지\n모두 좋아요 :)"
    const val CREATE_BOX_ADD_LETTER_OVER_FLOW = "최대 200자까지 작성할 수 있어요"

    const val CREATE_BOX_MOTION_TITLE = "이제 선물박스를\n채우러 가볼까요?"

    const val CREATE_BOX_STICKER_TITLE = "스티커 붙이기"
    const val CREATE_BOX_STICKER_DESCRIPTION = "최대 2개끼지 붙일 수 있어요"

    const val CREATE_BOX_ADD_GIFT_TITLE = "준비한 선물이 있나요?"
    const val CREATE_BOX_ADD_GIFT_DESCRIPTION = "선물 이미지를 담아서 친구에게 보여줄 수 있어요"
    const val CREATE_BOX_ADD_GIFT_CLOSE = "안 담을래요"

    const val CREATE_BOX_CHANGE_BOX_TITLE = "박스 고르기"
    const val CREATE_BOX_CHANGE_BOX_DESCRIPTION = "마음에 드는 선물박스를 골라주세요"

    const val CREATE_BOX_ADD_TITLE_TITLE = "마지막으로 선물박스에\n이름을 붙여주세요"
    const val CREATE_BOX_ADD_TITLE_DESCRIPTION = "선물박스의 이름을 붙여 특별함을 더해요\n붙인 이름은 받는 분에게도 보여져요"

    const val CREATE_BOX_ADD_SHARE_TITLE = "에게 선물박스를 보내보세요"
    const val CREATE_BOX_ADD_SHARE_SEND_KAKAO = "카카오톡으로 보내기"
    const val CREATE_BOX_ADD_SHARE_LAZY = "나중에 보낼래요"

    // GiftBox
    fun GIFT_BOX_ARR_TITLE(name: String) = "${name}님이 보낸\n선물박스가 도착했어요!"
    const val GIFT_BOX_OPEN_GIFT = "선물박스를 열어보세요"
    const val GIFT_BOX_ERROR_MESSAGE = "이 선물박스는 열 수 없어요"
    const val GIFT_BOX_ERROR_ALREADY_OPENED = "선물박스는 한 명만 열 수 있어요.\n선물박스의 주인이라면 패키에게 문의해주세요."

}