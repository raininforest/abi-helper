package repository

import common.model.Property

internal class PropertyRepositoryImpl : PropertyRepository {

    private val _properties = mutableSetOf<Property>()

    private var _action: ((List<Property>) -> Unit) = { _ -> }

    override fun propertyAdded(property: Property) {
        _properties.add(property)
        _action.invoke(_properties.toList())
    }

    override fun onUpdate(action: (List<Property>) -> Unit) {
        _action = action
    }
}