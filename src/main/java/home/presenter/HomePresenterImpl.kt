package home.presenter

import common.di.AbiHelperInjector
import common.editor.DataClassEditor
import common.repository.PropertyRepository
import common.mvp.View
import common.model.Parameter
import home.view.HomeView
import input.view.InputForm

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