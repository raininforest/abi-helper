package common.editor

import common.model.Parameter

internal interface DataClassEditor {
    fun edit(newParameters: List<Parameter>)
}
