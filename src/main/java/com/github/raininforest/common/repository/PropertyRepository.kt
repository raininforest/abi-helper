package com.github.raininforest.common.repository

import com.github.raininforest.common.model.Parameter

internal interface PropertyRepository {
    fun propertyAdded(parameter: Parameter)
    fun onUpdate(action: (List<Parameter>) -> Unit)

    val properties: List<Parameter>
}

