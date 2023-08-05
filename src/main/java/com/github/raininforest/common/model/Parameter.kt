package com.github.raininforest.common.model

internal data class Parameter(
    val valOrVar: ValOrVar = ValOrVar.VAL,
    val propertyName: String = "",
    val propertyType: String = "",
    val propertyDefaultValue: String? = null
)
