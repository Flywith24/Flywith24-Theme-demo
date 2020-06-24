package com.flywith24.theme_demo

import androidx.annotation.StyleRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Flywith24
 * @date   2020/6/23
 * time   19:13
 * description
 */
class ThemeViewModel : ViewModel() {
    val primaryColor = MutableLiveData<@StyleRes Int>()
    val edgeToEdgeEnabled = MutableLiveData<Boolean>(false)
}