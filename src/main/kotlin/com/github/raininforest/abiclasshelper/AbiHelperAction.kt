package com.github.raininforest.abiclasshelper

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.psi.PsiElement
import common.di.AbiHelperInjectorImpl
import home.view.AbiHelperForm
import org.jetbrains.kotlin.idea.core.util.toPsiFile


class AbiHelperAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            val editor = FileEditorManager.getInstance(project)
            val currentFile = editor?.selectedEditor?.file?.toPsiFile(project)
            //currentFile.addAfter()

            println("currentFile: ${currentFile?.name} \nchildrens: ${currentFile?.children}")
            val dialog = AbiHelperForm(AbiHelperInjectorImpl(project))
            dialog.show()
        }
    }
}