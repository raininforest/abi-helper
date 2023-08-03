package home.presenter

import common.di.AbiHelperInjector
import repository.PropertyRepository
import common.View
import common.model.Property
import home.view.AbiHelperView
import input.view.InputForm

internal class AbiHelperPresenterImpl(private val injector: AbiHelperInjector) : AbiHelperPresenter {
    private var _view: AbiHelperView? = null

    private val propertyRepository: PropertyRepository = injector.propertyRepository

    override fun addPropertyButtonClicked() {
        InputForm(injector).show()
    }

    override fun bindView(view: View) {
        _view = view as? AbiHelperView
        propertyRepository.onUpdate {
            _view?.showProperties(it.toUiProperties())
        }
    }

    override fun unbindView() {
        _view = null
    }

    private fun List<Property>.toUiProperties(): List<String> =
        map {
            "${it.valOrVar.value} ${it.propertyName}: ${it.propertyType} = ${it.propertyDefaultValue}"
        }
}