package input.presenter

import common.model.ValOrVar
import common.Presenter

internal interface InputPresenter : Presenter {
    fun okPressed()
    fun valOrVarChanged(valOrVar: ValOrVar)
    fun propertyNameTextChanged(text: String)
    fun propertyTypeTextChanged(text: String)
    fun defaultValueTextChanged(text: String)
}