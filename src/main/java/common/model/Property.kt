package common.model

internal data class Property(
    val valOrVar: ValOrVar = ValOrVar.VAL,
    val propertyName: String = "",
    val propertyType: String = "",
    val propertyDefaultValue: String? = null
)
