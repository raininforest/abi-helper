package input.presenter

import common.di.AbiHelperInjector
import common.View
import common.model.Property
import common.model.ValOrVar
import input.view.InputView

internal class InputPresenterImpl(injector: AbiHelperInjector) : InputPresenter {

    private val propertyRepository = injector.propertyRepository

    private var property: Property = Property()

    private var _view: InputView? = null

    override fun valOrVarChanged(valOrVar: ValOrVar) {
        println("valOrVarChanged: $valOrVar")
        property = property.copy(valOrVar = valOrVar)
    }

    override fun propertyNameTextChanged(text: String) {
        println("propertyNameTextChanged: $text")
        property = property.copy(propertyName = text)
    }

    override fun propertyTypeTextChanged(text: String) {
        println("propertyTypeTextChanged: $text")
        property = property.copy(propertyType = text)
    }

    override fun defaultValueTextChanged(text: String) {
        println("defaultValueTextChanged: $text")
        property = property.copy(propertyDefaultValue = text)
    }

    override fun bindView(view: View) {
        _view = view as? InputView
    }

    override fun unbindView() {
        _view = null
    }

    override fun okPressed() {
        println("okPressed: $property")
        propertyRepository.propertyAdded(property)
    }
}