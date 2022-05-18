package com.thana.kotlin_ide


import Client_ID
import Client_Secret
import kotlinx.serialization.Serializable

@Serializable
data class IdeRequest(
    val script: String = "",
    val language: String = "kotlin",
    val versionIndex: String = "1",
    val clientId: String = Client_ID,
    val clientSecret: String = Client_Secret
)

