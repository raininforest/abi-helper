package common.repository

import common.model.Parameter

internal interface PropertyRepository {
    fun propertyAdded(parameter: Parameter)
    fun onUpdate(action: (List<Parameter>) -> Unit)

    val properties: List<Parameter>
}

