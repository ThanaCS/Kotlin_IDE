package com.thana.kotlin_ide


import kotlinx.serialization.Serializable

@Serializable
data class IdeRequest(
    val script: String = "",
    val language: String = "kotlin",
    val versionIndex: String = "1",
    val clientId: String = "c0b208d49615dfa450e83b7a20d05fd5",
    val clientSecret: String = "22d0c5a0dc5058ce9928578a883c721cc4e84ffcb4ba72c9bf690c3999fa70e"
)

