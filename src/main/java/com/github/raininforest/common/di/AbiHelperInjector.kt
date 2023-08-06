package com.github.raininforest.common.di

import com.github.raininforest.common.editor.DataClassEditor
import com.github.raininforest.common.editor.DataClassEditorImpl
import com.github.raininforest.common.repository.PropertyRepository
import com.github.raininforest.common.repository.PropertyRepositoryImpl
import com.github.raininforest.common.repository.SettingStoreImpl
import com.github.raininforest.common.repository.SettingsStore
import com.github.raininforest.home.presenter.HomePresenter
import com.github.raininforest.home.presenter.HomePresenterImpl
import com.github.raininforest.input.presenter.InputPresenter
import com.github.raininforest.input.presenter.InputPresenterImpl
import com.intellij.openapi.project.Project

internal interface AbiHelperInjector {
    val projectInstance: Project
    val homePresenter: HomePresenter
    val inputPresenter: InputPresenter
    val propertyRepository: PropertyRepository
    val dataClassEditor: DataClassEditor
    val settingsStore: SettingsStore
}

internal class AbiHelperInjectorImpl(private val project: Project) : AbiHelperInjector {
    override val projectInstance: Project
        get() = project

    override val homePresenter: HomePresenter
        get() = HomePresenterImpl(this)

    override val inputPresenter: InputPresenter
        get() = InputPresenterImpl(this)

    override val propertyRepository: PropertyRepository by lazy { PropertyRepositoryImpl() }

    override val dataClassEditor: DataClassEditor by lazy { DataClassEditorImpl(projectInstance, settingsStore) }

    override val settingsStore: SettingsStore by lazy {
        SettingStoreImpl(shouldGenerateConstructor = true, shouldGenerateCopy = false)
    }
}