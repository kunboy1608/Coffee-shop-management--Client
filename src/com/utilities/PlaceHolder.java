/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utilities;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author kunbo
 */
public class PlaceHolder implements FocusListener {

    private String text;
    private JTextField txtField;

    private PlaceHolder() {

    }

    public PlaceHolder(String text, JTextField textField) {
        this.text = text;
        this.txtField = textField;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (txtField.getText().equals(text)) {
            txtField.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (txtField.getText().isEmpty()) {
            txtField.setText(text);
        }
    }

}
