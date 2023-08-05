package input.presenter

import common.di.AbiHelperInjector
import common.mvp.View
import common.model.Parameter
import common.model.ValOrVar
import input.view.InputView

internal class InputPresenterImpl(injector: AbiHelperInjector) : InputPresenter {

    private val propertyRepository = injector.propertyRepository

    private var parameter: Parameter = Parameter()

    private var _view: InputView? = null

    override fun valOrVarChanged(valOrVar: ValOrVar) {
        println("valOrVarChanged: $valOrVar")
        parameter = parameter.copy(valOrVar = valOrVar)
    }

    override fun propertyNameTextChanged(text: String) {
        println("propertyNameTextChanged: $text")
        parameter = parameter.copy(propertyName = text)
    }

    override fun propertyTypeTextChanged(text: String) {
        println("propertyTypeTextChanged: $text")
        parameter = parameter.copy(propertyType = text)
    }

    override fun defaultValueTextChanged(text: String) {
        println("defaultValueTextChanged: $text")
        parameter = parameter.copy(propertyDefaultValue = text)
    }

    override fun bindView(view: View) {
        _view = view as? InputView
    }

    override fun unbindView() {
        _view = null
    }

    override fun okPressed() {
        println("okPressed: $parameter")
        propertyRepository.propertyAdded(parameter)
    }
}