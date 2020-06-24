package com.flywith24.theme_demo

import androidx.appcompat.app.AppCompatDelegate

/**
 * @author Flywith24
 * @date   2020/6/24
 * time   10:02
 * description
 */
enum class Theme(val mode: Int) {
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO),
    DARK(AppCompatDelegate.MODE_NIGHT_YES),
    SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}