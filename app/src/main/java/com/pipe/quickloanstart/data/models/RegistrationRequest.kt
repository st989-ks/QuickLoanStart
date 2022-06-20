package com.pipe.quickloanstart.data.models

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String
)