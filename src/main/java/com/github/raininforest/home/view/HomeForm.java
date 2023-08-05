package com.github.raininforest.home.view;

import com.intellij.openapi.ui.DialogWrapper;
import com.github.raininforest.common.di.AbiHelperInjector;
import com.github.raininforest.home.presenter.HomePresenter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class HomeForm extends DialogWrapper implements HomeView {

    private final HomePresenter presenter;

    public HomeForm(AbiHelperInjector injector) {
        super(injector.getProjectInstance());
        init();

        this.presenter = injector.getHomePresenter();
        this.presenter.bindView(this);

        addPropertyButton.addActionListener(e -> presenter.addPropertyButtonClicked());
    }

    @Override
    public @Nullable JComponent createCenterPanel() {
        return mainPanel;
    }

    @Override
    protected void doOKAction() {
        presenter.okPressed();
        presenter.unbindView();
        super.doOKAction();
    }

    @Override
    public void doCancelAction() {
        super.doCancelAction();
    }

    private JPanel mainPanel;
    private JButton addPropertyButton;
    private JTextArea textArea;
    private JLabel infoLabel;

    @Override
    public void showProperties(@NotNull List<String> properties) {
        textArea.setText("");
        properties.forEach(s -> textArea.append(s + "\n"));
    }
}