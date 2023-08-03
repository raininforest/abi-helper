package home.view;

import com.intellij.openapi.ui.DialogWrapper;
import common.di.AbiHelperInjector;
import home.presenter.AbiHelperPresenter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class AbiHelperForm extends DialogWrapper implements AbiHelperView {

    private final AbiHelperPresenter presenter;

    public AbiHelperForm(AbiHelperInjector injector) {
        super(injector.getProjectInstance());
        init();

        this.presenter = injector.getAbiHelperPresenter();
        this.presenter.bindView(this);

        addPropertyButton.addActionListener(e -> presenter.addPropertyButtonClicked());
    }

    @Override
    public @Nullable JComponent createCenterPanel() {
        return mainPanel;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
    }

    @Override
    public void doCancelAction() {
        super.doCancelAction();
    }

    private JPanel mainPanel;
    private JButton addPropertyButton;
    private JTextArea textArea;

    @Override
    public void showProperties(@NotNull List<String> properties) {
        textArea.removeAll();
        properties.forEach(s -> textArea.append(s + "\n"));
    }
}
