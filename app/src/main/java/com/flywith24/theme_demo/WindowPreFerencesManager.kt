package com.flywith24.theme_demo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.google.android.material.color.MaterialColors

/**
 * @author Flywith24
 * @date   2020/6/24
 * time   9:17
 * description
 */

private const val EDGE_TO_EDGE_BAR_ALPHA = 128
private const val EDGE_TO_EDGE_FLAGS =
    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

/**
 * 设置是否全屏（状态栏和导航栏不占空间）
 */
@SuppressLint("ObsoleteSdkInt")
fun AppCompatActivity.applyEdgeToEdgePreference(edgeToEdgeEnabled: Boolean) {
    if (SDK_INT < LOLLIPOP) return
    val statusBarColor = getStatusBarColor(edgeToEdgeEnabled, this)
    val navBarColor = getNavBarColor(edgeToEdgeEnabled, this)

    val lightBackground =
        isColorLight(MaterialColors.getColor(this, android.R.attr.colorBackground, Color.BLACK))
    val lightNavBar = isColorLight(navBarColor)
    val showDarkNavBarIcons =
        lightNavBar || navBarColor == TRANSPARENT && lightBackground

    with(window) {
        val currentStatusBar =
            if (SDK_INT >= M) decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        val currentNavBar =
            if (showDarkNavBarIcons && SDK_INT >= O) View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else 0

        this.navigationBarColor = navBarColor
        this.statusBarColor = statusBarColor
        val systemUiVisibility =
            ((if (edgeToEdgeEnabled) EDGE_TO_EDGE_FLAGS else View.SYSTEM_UI_FLAG_VISIBLE)
                    or currentStatusBar
                    or currentNavBar)

        decorView.systemUiVisibility = systemUiVisibility
    }
}

/**
 * 获取状态栏颜色
 */
@SuppressLint("ObsoleteSdkInt")
fun getStatusBarColor(edgeToEdgeEnabled: Boolean, context: Context): Int = when {
    //低版本
    edgeToEdgeEnabled && SDK_INT < M -> {
        val opaqueStatusBarColor =
            MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK)
        ColorUtils.setAlphaComponent(opaqueStatusBarColor, EDGE_TO_EDGE_BAR_ALPHA)
    }
    edgeToEdgeEnabled -> TRANSPARENT
    else -> MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK)
}

/**
 * 获取导航栏颜色
 */
fun getNavBarColor(edgeToEdgeEnabled: Boolean, context: Context): Int = when {
    //低版本
    edgeToEdgeEnabled && SDK_INT < O_MR1 -> {
        val opaqueStatusBarColor =
            MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK)
        ColorUtils.setAlphaComponent(opaqueStatusBarColor, EDGE_TO_EDGE_BAR_ALPHA)
    }
    edgeToEdgeEnabled -> TRANSPARENT
    else -> MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK)
}

fun isColorLight(@ColorInt color: Int): Boolean {
    return color != TRANSPARENT && ColorUtils.calculateLuminance(color) > 0.5
}