package common.di

import com.intellij.openapi.project.Project
import common.editor.DataClassEditor
import common.editor.DataClassEditorImpl
import home.presenter.HomePresenter
import home.presenter.HomePresenterImpl
import input.presenter.InputPresenter
import input.presenter.InputPresenterImpl
import common.repository.PropertyRepository
import common.repository.PropertyRepositoryImpl

internal interface AbiHelperInjector {
    val projectInstance: Project?
    val homePresenter: HomePresenter
    val inputPresenter: InputPresenter
    val propertyRepository: PropertyRepository
    val dataClassEditor: DataClassEditor
}

internal class AbiHelperInjectorImpl(private val project: Project) : AbiHelperInjector {
    override val projectInstance: Project
        get() = project

    override val homePresenter: HomePresenter
        get() = HomePresenterImpl(this)

    override val inputPresenter: InputPresenter
        get() = InputPresenterImpl(this)

    override val propertyRepository: PropertyRepository by lazy { PropertyRepositoryImpl() }

    override val dataClassEditor: DataClassEditor by lazy { DataClassEditorImpl(projectInstance) }
}