package com.github.raininforest.home.view;

import com.intellij.openapi.ui.DialogWrapper;
import com.github.raininforest.common.di.AbiHelperInjector;
import com.github.raininforest.home.presenter.HomePresenter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class HomeForm extends DialogWrapper implements HomeView {

    private final HomePresenter presenter;

    public HomeForm(AbiHelperInjector injector) {
        super(injector.getProjectInstance());
        init();
        setTitle("Abi Helper");

        this.presenter = injector.getHomePresenter();
        this.presenter.bindView(this);

        addPropertyButton.addActionListener(e -> presenter.addPropertyButtonClicked());
        constructorCheckbox.addItemListener(e -> {
            final boolean isSelected;
            if (e.getStateChange() == ItemEvent.SELECTED) isSelected = true;
            else if (e.getStateChange() == ItemEvent.DESELECTED) isSelected = false;
            else return;
            presenter.constructorCheckboxChecked(isSelected);
        });
        copyCheckbox.addItemListener(e -> {
            final boolean isSelected;
            if (e.getStateChange() == ItemEvent.SELECTED) isSelected = true;
            else if (e.getStateChange() == ItemEvent.DESELECTED) isSelected = false;
            else return;
            presenter.copyCheckboxChecked(isSelected);
        });
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
    private JCheckBox constructorCheckbox;
    private JCheckBox copyCheckbox;

    @Override
    public void showProperties(@NotNull List<String> properties) {
        textArea.setText("");
        properties.forEach(s -> textArea.append(s + "\n"));
    }
}
