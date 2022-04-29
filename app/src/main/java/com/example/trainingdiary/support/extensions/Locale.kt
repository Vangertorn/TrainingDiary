package com.example.trainingdiary.support.extensions

import java.util.*

val Locale.flagEmoji: String
    get() {
        val firstLetter =
            Character.codePointAt(country, 0) - ALPHABET_UNICODE_CHAR + REGIONAL_INDICATOR
        val secondLetter =
            Character.codePointAt(country, 1) - ALPHABET_UNICODE_CHAR + REGIONAL_INDICATOR
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }

private const val ALPHABET_UNICODE_CHAR = 0x41
private const val REGIONAL_INDICATOR = 0x1F1E6