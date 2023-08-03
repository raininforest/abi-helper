package common.model

internal enum class ValOrVar(val value: String) {
    VAL("val"),
    VAR("var")
}

internal val String.toValOrVar: ValOrVar
    get() = when (this) {
        ValOrVar.VAL.value -> ValOrVar.VAL
        ValOrVar.VAR.value -> ValOrVar.VAR
        else -> ValOrVar.VAL
    }