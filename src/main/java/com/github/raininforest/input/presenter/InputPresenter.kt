package com.github.raininforest.input.presenter

import com.github.raininforest.common.model.ValOrVar
import com.github.raininforest.common.mvp.Presenter

internal interface InputPresenter : Presenter {
    fun okPressed()
    fun valOrVarChanged(valOrVar: ValOrVar)
    fun propertyNameTextChanged(text: String)
    fun propertyTypeTextChanged(text: String)
    fun defaultValueTextChanged(text: String)
}