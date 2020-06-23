package com.flywith24.theme_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a = ContextCompat.getColor(this, R.color.purple_500)
        val b = ContextCompat.getColor(btnTheme.context, R.color.purple_500)
        Log.d(TAG, "onCreate: $a")
        Log.d(TAG, "onCreate: $b")

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.themeLight ->
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO

                R.id.themeNight ->
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES

                R.id.themeDefault->
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
//        themeLight.isChecked = true

    }

    companion object {
        private const val TAG = "yyz11"
    }
}