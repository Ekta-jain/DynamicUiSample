package com.e4ekta.network_module.model


import com.google.gson.annotations.SerializedName

data class UiData(
    @SerializedName("hint")
    val hint: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("uitype")
    val uitype: String,
    @SerializedName("value")
    val value: String
)