import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import common.di.AbiHelperInjectorImpl
import home.view.HomeForm


class AbiHelperAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            val dialog = HomeForm(AbiHelperInjectorImpl(project))
            dialog.show()
        }
    }
}
