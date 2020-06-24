package com.flywith24.theme_demo

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.CompoundButtonCompat
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mThemeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        //配置 primaryColor 是否全屏，在配置布局之前
        with(mThemeViewModel) {
            primaryColor.value?.let { setTheme(it) }
            edgeToEdgeEnabled.value?.let { applyEdgeToEdgePreference(it) }
        }

        super.onCreate(savedInstanceState)

        initPrimaryRadioButton()
        initToolbar()

        mThemeViewModel.currentTheme.observe(this) { delegate.localNightMode = it.mode }

        initThemeRadioButton()
    }

    private fun initThemeRadioButton() {
        rgTheme.check(getThemeCheckId())
        rgTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.themeLight -> mThemeViewModel.setCurrentTheme(Theme.LIGHT)
                R.id.themeDark -> mThemeViewModel.setCurrentTheme(Theme.DARK)
                R.id.themeDefault -> mThemeViewModel.setCurrentTheme(Theme.SYSTEM)
            }
        }
    }

    /**
     * 根据 mThemeViewModel.currentTheme 选中默认的 theme radio button
     */
    private fun getThemeCheckId(): Int {
        return when (mThemeViewModel.currentTheme.value) {
            Theme.LIGHT -> R.id.themeLight
            Theme.DARK -> R.id.themeDark
            Theme.SYSTEM -> R.id.themeDefault
            else -> -1
        }
    }

    private fun initToolbar() {
        val edgeToEdgeEnabled = mThemeViewModel.edgeToEdgeEnabled.value ?: false
        toolbar1.setNavigationIcon(if (edgeToEdgeEnabled) R.drawable.ic_edge_to_edge_disable_24dp else R.drawable.ic_edge_to_edge_enable_24dp)
        toolbar1.setNavigationOnClickListener {
            mThemeViewModel.edgeToEdgeEnabled.value = !edgeToEdgeEnabled
            recreate()
        }
    }

    private fun initPrimaryRadioButton() {
        createPrimaryButton()
        rgPrimary.setOnCheckedChangeListener { group, checkedId ->
            mThemeViewModel.primaryColor.value =
                group.findViewById<AppCompatRadioButton>(checkedId).tag as Int
            recreate()
        }
    }


    private fun createPrimaryButton() {
        val primaryArray = resources.obtainTypedArray(R.array.primary_palettes)
        val descriptionArray = resources.obtainTypedArray(R.array.palettes_content_description)
        var style: Int
        if (primaryArray.length() != descriptionArray.length()) {
            throw IllegalArgumentException("primary_palettes 与 palettes_content_description 长度不等！")
        }
        for (index in 0 until primaryArray.length()) {
            style = primaryArray.getResourceId(index, 0)
            rgPrimary.addView(AppCompatRadioButton(this).apply {
                text = descriptionArray.getString(index)
                val a = obtainStyledAttributes(style, PRIMARY_THEME_OVERLAY_ATTRS)
                CompoundButtonCompat.setButtonTintList(
                    this, ColorStateList.valueOf(a.getColor(0, Color.TRANSPARENT))
                )
                // 与 mThemeViewModel.primaryColor 一致则选中
                isChecked = mThemeViewModel.primaryColor.value == style
                tag = style
                a.recycle()
            })
        }
        primaryArray.recycle()
        descriptionArray.recycle()
    }

    companion object {

        @StyleableRes
        private val PRIMARY_THEME_OVERLAY_ATTRS = intArrayOf(
            R.attr.colorPrimary, R.attr.colorPrimaryDark
        )
    }
}