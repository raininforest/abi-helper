package common.di

import com.intellij.openapi.project.Project
import home.presenter.AbiHelperPresenter
import home.presenter.AbiHelperPresenterImpl
import input.presenter.InputPresenter
import input.presenter.InputPresenterImpl
import repository.PropertyRepository
import repository.PropertyRepositoryImpl

internal interface AbiHelperInjector {
    val projectInstance: Project?
    val abiHelperPresenter: AbiHelperPresenter
    val inputPresenter: InputPresenter
    val propertyRepository: PropertyRepository
}

internal class AbiHelperInjectorImpl(private val project: Project?) : AbiHelperInjector {
    override val projectInstance: Project?
        get() = project

    override val abiHelperPresenter: AbiHelperPresenter
        get() = AbiHelperPresenterImpl(this)

    override val inputPresenter: InputPresenter
        get() = InputPresenterImpl(this)

    override val propertyRepository: PropertyRepository by lazy { PropertyRepositoryImpl() }
}