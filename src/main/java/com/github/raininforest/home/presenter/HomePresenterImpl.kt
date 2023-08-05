package com.github.raininforest.home.presenter

import com.github.raininforest.common.di.AbiHelperInjector
import com.github.raininforest.common.editor.DataClassEditor
import com.github.raininforest.common.repository.PropertyRepository
import com.github.raininforest.common.mvp.View
import com.github.raininforest.common.model.Parameter
import com.github.raininforest.home.view.HomeView
import com.github.raininforest.input.view.InputForm

internal class HomePresenterImpl(private val injector: AbiHelperInjector) : HomePresenter {
    private var _view: HomeView? = null

    private val propertyRepository: PropertyRepository = injector.propertyRepository
    private val dataClassEditor: DataClassEditor = injector.dataClassEditor

    override fun addPropertyButtonClicked() {
        InputForm(injector).show()
    }

    override fun okPressed() {
        dataClassEditor.edit(propertyRepository.properties)
    }

    override fun bindView(view: View) {
        _view = view as? HomeView
        propertyRepository.onUpdate {
            _view?.showProperties(it.toUiProperties())
        }
    }

    override fun unbindView() {
        _view = null
    }

    private fun List<Parameter>.toUiProperties(): List<String> =
        map {
            "${it.valOrVar.value} ${it.propertyName}: ${it.propertyType} = ${it.propertyDefaultValue}"
        }
}