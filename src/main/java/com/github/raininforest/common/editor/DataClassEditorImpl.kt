package com.github.raininforest.common.editor

import com.github.raininforest.common.model.Parameter
import com.github.raininforest.common.repository.SettingsStore
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.core.util.toPsiFile
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType

internal class DataClassEditorImpl(
    private val project: Project,
    private val settingsStore: SettingsStore
) : DataClassEditor {

    private val factory = KtPsiFactory(project, true)

    override fun edit(newParameters: List<Parameter>) {
        if (newParameters.isEmpty()) return

        val editor = FileEditorManager.getInstance(project)
        val currentFile = editor?.selectedEditor?.file?.toPsiFile(project)

        val dataClass = currentFile?.children
            ?.filterIsInstance<KtClass>()
            ?.firstOrNull { it.isData() } ?: throw IllegalStateException("data class is null")

        WriteCommandAction.runWriteCommandAction(project) {
            val existingParameters = dataClass.copyParameters()
            val newKtParameters = newParameters.mapToKtParameters()

            dataClass.addParametersToPrimaryConstructor(newParameters = newKtParameters)
            if (settingsStore.shouldGenerateConstructor) {
                dataClass.addSecondaryConstructor(existingParameters, newKtParameters)
            }
            if (settingsStore.shouldGenerateCopy) {
                dataClass.addCopy(existingParameters, newKtParameters)
            }
        }
    }

    private fun KtClass.copyParameters(): List<KtParameter> = mutableListOf<KtParameter>()
        .also {
            it.addAll(primaryConstructorParameters)
            it.toList()
        }

    private fun List<Parameter>.mapToKtParameters(): List<KtParameter> =
        map { p ->
            val defaultValueSubString =
                if (p.propertyDefaultValue.isNullOrBlank()) ""
                else " = ${p.propertyDefaultValue}"
            factory.createParameter(
                text = "${p.valOrVar.value} ${p.propertyName}: ${p.propertyType}$defaultValueSubString"
            )
        }

    private fun KtClass.addSecondaryConstructor(
        existingParameters: List<KtParameter>,
        newParameters: List<KtParameter>
    ) {
        val body = getOrCreateBody()

        val startConstructor = "constructor(\n"
        val startThis = "this(\n"
        val rBrace = ")"

        val secondaryConstructorParameters = mutableListOf<String>()
        secondaryConstructorParameters.addAll(
            existingParameters.map { ktParameter ->
                val mutableParameter: KtParameter = ktParameter.copy() as KtParameter
                mutableParameter.valOrVarKeyword?.delete()
                mutableParameter.modifierList?.delete()
                val parameterText = mutableParameter.text.trimStart()
                "$parameterText,\n"
            }
        )

        val thisParameters = mutableListOf<String>()
        thisParameters.addAll(existingParameters.map { "${it.name} = ${it.name},\n" })
        thisParameters.addAll(newParameters.mapIndexed { i, it ->
            val thisParameterPattern = "${it.name} = ${it.defaultValue?.text}"
            if (i != newParameters.lastIndex) "$thisParameterPattern,\n"
            else "$thisParameterPattern\n"
        })

        val constructor = factory.createSecondaryConstructor(
            "$startConstructor${secondaryConstructorParameters.joinToString(separator = "")}$rBrace : $startThis${
                thisParameters.joinToString(separator = "")
            }$rBrace"
        )

        body.addAfter(constructor, body.lBrace)
    }

    private fun KtClass.addCopy(
        existingParameters: List<KtParameter>,
        newParameters: List<KtParameter>
    ) {
        val body = getOrCreateBody()
        val startCopy = "copy(\n"
        val rBrace = ")"

        val originalCopyParameters = mutableListOf<String>()
        originalCopyParameters.addAll(
            existingParameters.map { ktParameter ->
                val mutableParameter = ktParameter.copyNameAndTypeOnly()
                val parameterText = mutableParameter.text.trimStart().trimEnd()
                "$parameterText = this.${mutableParameter.name},\n"
            }
        )

        val delegationCopyParameters = mutableListOf<String>()
        delegationCopyParameters.addAll(
            existingParameters.map { ktParameter ->
                val parameterName = ktParameter.name
                "$parameterName = $parameterName,\n"
            }
        )
        delegationCopyParameters.addAll(
            newParameters.mapIndexed { i, ktParameter ->
                val parameterName = ktParameter.name
                val copyParameterPattern = "$parameterName = this.$parameterName"
                if (i != newParameters.lastIndex) "$copyParameterPattern,\n"
                else "$copyParameterPattern\n"
            }
        )

        val copy = factory.createFunction(
            "fun $startCopy${originalCopyParameters.joinToString(separator = "")}$rBrace = $startCopy${
                delegationCopyParameters.joinToString(separator = "")
            }$rBrace"
        )

        val secondaryConstructors = body.getChildrenOfType<KtSecondaryConstructor>()
        val anchorElement = if (secondaryConstructors.isEmpty()) body.lBrace
            else secondaryConstructors.last()
        val addedCopy = body.addAfter(copy, anchorElement)
        val annotationElement = factory.createAnnotationEntry(
            text = "\n@Deprecated(message = \"For abi stability only\", level = DeprecationLevel.HIDDEN)"
        )
        body.addBefore(factory.createNewLine(2), addedCopy)
        body.addBefore(annotationElement, addedCopy)
    }
}
