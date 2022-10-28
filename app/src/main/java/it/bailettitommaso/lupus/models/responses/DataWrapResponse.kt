package it.bailettitommaso.lupus.models.responses

import com.google.gson.annotations.SerializedName

data class DataWrapResponse<T>(
    @field:SerializedName("data") val data: T
)
