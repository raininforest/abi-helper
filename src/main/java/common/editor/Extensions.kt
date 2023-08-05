package common.editor

import org.jetbrains.kotlin.idea.core.dropDefaultValue
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtParameter

internal fun KtParameter.copyNameAndTypeOnly(): KtParameter {
    val mutableParameter = copy() as KtParameter
    mutableParameter.modifierList?.delete()
    mutableParameter.valOrVarKeyword?.delete()
    mutableParameter.dropDefaultValue()
    return mutableParameter
}

internal fun KtClass.addParametersToPrimaryConstructor(
    newParameters: List<KtParameter>
) {
    if (primaryConstructorParameters.isEmpty()) throw IllegalStateException("data class parameters are empty")
    val parameterList = getPrimaryConstructorParameterList()
        ?: throw IllegalStateException("primary constructor parameter list is null")

    newParameters.forEach { parameter -> parameterList.addParameter(parameter) }
}
