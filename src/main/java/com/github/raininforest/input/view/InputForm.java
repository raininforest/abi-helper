package com.github.raininforest.input.view;

import com.github.raininforest.common.model.ValOrVarKt;
import com.intellij.openapi.ui.DialogWrapper;
import com.github.raininforest.common.di.AbiHelperInjector;
import com.github.raininforest.input.presenter.InputPresenter;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ItemEvent;

public class InputForm extends DialogWrapper {

    private JPanel inputPanel;
    private JComboBox valOrVarComboBox;
    private JTextField propertyNameField;
    private JLabel label;
    private JTextField propertyTypeTextField;
    private JLabel equalsLabel;
    private JTextField defaultValueTextField;

    private final InputPresenter presenter;

    public InputForm(AbiHelperInjector abiHelperInjector) {
        super(abiHelperInjector.getProjectInstance());
        init();
        setTitle("Enter constructor parameters");

        this.presenter = abiHelperInjector.getInputPresenter();

        initValOrVarComboBox();
        initPropertyNameField();
        initPropertyTypeField();
        initDefaultValueField();
    }

    private void initDefaultValueField() {
        defaultValueTextField.getDocument().addDocumentListener(new DocumentListener() {
            private void update(DocumentEvent e) {
                try {
                    presenter.defaultValueTextChanged(e.getDocument().getText(0, defaultValueTextField.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        });
    }

    private void initPropertyTypeField() {
        propertyTypeTextField.getDocument().addDocumentListener(new DocumentListener() {
            private void update(DocumentEvent e) {
                try {
                    presenter.propertyTypeTextChanged(e.getDocument().getText(0, propertyTypeTextField.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        });
    }

    private void initPropertyNameField() {
        propertyNameField.getDocument().addDocumentListener(new DocumentListener() {
            private void update(DocumentEvent e) {
                try {
                    presenter.propertyNameTextChanged(e.getDocument().getText(0, propertyNameField.getDocument().getLength()));
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        });
    }

    private void initValOrVarComboBox() {
        valOrVarComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String value = (String) e.getItem();
                presenter.valOrVarChanged(ValOrVarKt.getToValOrVar(value));
            }
        });
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return inputPanel;
    }

    @Override
    protected void doOKAction() {
        presenter.okPressed();
        presenter.unbindView();
        super.doOKAction();
    }

    @Override
    public void doCancelAction() {
        presenter.unbindView();
        super.doCancelAction();
    }
}
