package home.presenter

import common.mvp.Presenter

internal interface HomePresenter: Presenter {
    fun addPropertyButtonClicked()

    fun okPressed()
}

