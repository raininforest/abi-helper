package home.view

import common.mvp.View

internal interface HomeView: View {
    fun showProperties(properties: List<String>)
}