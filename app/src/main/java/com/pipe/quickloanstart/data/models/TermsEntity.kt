package com.pipe.quickloanstart.data.models

import com.google.gson.annotations.SerializedName

data class TermsEntity(
    @SerializedName("maxAmount") val maxAmount: Int,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int,
)