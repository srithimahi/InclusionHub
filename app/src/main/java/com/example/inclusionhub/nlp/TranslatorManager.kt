package com.example.inclusionhub.nlp


import kotlinx.coroutines.delay

class TranslatorManager {

    private val mockTranslations = mapOf(
        "es" to "[Spanish]",
        "hi" to "[Hindi]"
    )

    suspend fun translate(text: String, targetLang: String): String {
        delay(1000)
        val langPrefix = mockTranslations[targetLang] ?: "[Translated]"
        return "$langPrefix: $text"
    }
}
