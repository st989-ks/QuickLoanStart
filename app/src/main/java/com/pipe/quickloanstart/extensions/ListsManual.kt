package com.pipe.quickloanstart.extensions

import com.pipe.quickloanstart.R

data class ListManual(
    val image: Int,
    val title: Int,
    val text: Int
)
val manualData = listOf(
    ListManual(
        image = R.drawable.loan,
        title = R.string.onboarding_welcome_title,
        text = R.string.onboarding_welcome_message,
    ),
    ListManual(
        image = R.drawable.screen_start,
        title = R.string.onboarding_step1_title,
        text = R.string.onboarding_step1_message,
    ),
    ListManual(
        image = R.drawable.screen_get_loan_1,
        title = R.string.onboarding_step2_title,
        text = R.string.onboarding_step2_message,
    ),
    ListManual(
        image = R.drawable.screen_get_loan_2,
        title = R.string.onboarding_step3_title,
        text = R.string.onboarding_step3_message,
    ),
    ListManual(
        image = R.drawable.screen_statys,
        title = R.string.onboarding_step4_title,
        text = R.string.onboarding_step4_message,
    ),
    ListManual(
        image = R.drawable.screen_list_statys,
        title = R.string.onboarding_step5_title,
        text = R.string.onboarding_step5_message,
    ),
)