package it.bailettitommaso.lupus.models

import com.google.gson.annotations.SerializedName

data class ErrorBag(
    @field:SerializedName("message") val message: String,
    @field:SerializedName("errors") val validationErrors: Map<String, List<String>>? = mapOf()
)
