package com.github.raininforest.home.view

import com.github.raininforest.common.mvp.View

internal interface HomeView: View {
    fun showProperties(properties: List<String>)
}