package com.github.raininforest.common.repository

import com.github.raininforest.common.model.Parameter

internal class PropertyRepositoryImpl : PropertyRepository {

    private val _properties = mutableSetOf<Parameter>()

    private var _action: ((List<Parameter>) -> Unit) = { _ -> }

    override fun propertyAdded(parameter: Parameter) {
        _properties.add(parameter)
        _action.invoke(_properties.toList())
    }

    override fun onUpdate(action: (List<Parameter>) -> Unit) {
        _action = action
    }

    override val properties: List<Parameter>
        get() = _properties.toList()
}