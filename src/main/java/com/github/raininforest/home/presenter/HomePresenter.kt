package com.github.raininforest.home.presenter

import com.github.raininforest.common.mvp.Presenter

internal interface HomePresenter: Presenter {
    fun addPropertyButtonClicked()

    fun okPressed()
}

