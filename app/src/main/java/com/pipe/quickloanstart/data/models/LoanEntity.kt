package com.pipe.quickloanstart.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoanEntity(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("amount") val amount: Int,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int,
    @SerializedName("date") val date: String,
    @SerializedName("state") val state: String,
    @SerializedName("id") val id: Int,
)