package com.flywith24.theme_demo

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.CompoundButtonCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mViewModel by viewModels<ThemeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel.primaryColor.value?.let {
            setTheme(it)
        }

        super.onCreate(savedInstanceState)

        initRadioButton()

        rgPrimary.setOnCheckedChangeListener { group, checkedId ->
            mViewModel.primaryColor.value =
                group.findViewById<AppCompatRadioButton>(checkedId).tag as Int
            recreate()
        }
        rgTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.themeLight ->
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO

                R.id.themeNight ->
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES

                R.id.themeDefault ->
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
//        themeLight.isChecked = true

    }

    private fun initRadioButton() {
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
                tag = style
                a.recycle()
            })
        }
        primaryArray.recycle()
        descriptionArray.recycle()
    }

    companion object {
        private const val TAG = "yyz11"

        @StyleableRes
        private val PRIMARY_THEME_OVERLAY_ATTRS = intArrayOf(
            R.attr.colorPrimary, R.attr.colorPrimaryDark
        )
    }
}