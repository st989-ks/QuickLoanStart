package com.pipe.quickloanstart.data.models

import com.google.gson.annotations.SerializedName

class RegistrationEntity(
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String
)