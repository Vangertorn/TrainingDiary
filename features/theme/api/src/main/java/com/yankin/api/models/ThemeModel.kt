package com.yankin.api.models

sealed interface ThemeModel {
    val intCode: Int

    object Light : ThemeModel {
        override val intCode = 111
    }

    object Dark : ThemeModel {
        override val intCode = 222
    }

    object Night : ThemeModel {
        override val intCode = 333
    }

    companion object {
        fun fromCode(intCode: Int): ThemeModel = when (intCode) {
            Light.intCode -> Light
            Dark.intCode -> Dark
            Night.intCode -> Night
            else -> Light
        }

        fun ThemeModel?.isNight(): Boolean = this != null && this != Light
    }
}