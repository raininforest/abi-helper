package com.github.raininforest

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.github.raininforest.common.di.AbiHelperInjectorImpl
import com.github.raininforest.home.view.HomeForm


class AbiHelperAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            val dialog = HomeForm(AbiHelperInjectorImpl(project))
            dialog.show()
        }
    }
}
