package com.e4ekta.network_module.model


import com.google.gson.annotations.SerializedName

data class AssignmentResponse(
    @SerializedName("heading-text")
    val headingText: String,
    @SerializedName("logo-url")
    val logoUrl: String,
    @SerializedName("uidata")
    val uidata: List<UiData>
)