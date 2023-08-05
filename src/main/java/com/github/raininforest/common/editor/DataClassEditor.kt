package com.github.raininforest.common.editor

import com.github.raininforest.common.model.Parameter

internal interface DataClassEditor {
    fun edit(newParameters: List<Parameter>)
}
