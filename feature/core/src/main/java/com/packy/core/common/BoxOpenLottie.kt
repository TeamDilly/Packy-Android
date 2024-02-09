package com.packy.core.common

import androidx.annotation.RawRes
import com.packy.feature.core.R

enum class BoxOpenLottie(
    @RawRes val lottie: Int,
    @RawRes val openLottie: Int
) {
    BOX_OPEN_1(R.raw.box_motion_make_1,R.raw.box_motion_arr_1),
    BOX_OPEN_2(R.raw.box_motion_make_2,R.raw.box_motion_arr_2),
    BOX_OPEN_3(R.raw.box_motion_make_3,R.raw.box_motion_arr_3),
    BOX_OPEN_4(R.raw.box_motion_make_4,R.raw.box_motion_arr_4),
    BOX_OPEN_5(R.raw.box_motion_make_5,R.raw.box_motion_arr_5),
    BOX_OPEN_6(R.raw.box_motion_make_6,R.raw.box_motion_arr_6);
}