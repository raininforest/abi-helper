package repository

import common.model.Property

internal interface PropertyRepository {
    fun propertyAdded(property: Property)
    fun onUpdate(action: (List<Property>) -> Unit)
}

