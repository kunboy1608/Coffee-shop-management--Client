/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.view.panel;

import com.controller.DoUongController;
import com.handle.LanguageHandle;
import com.handle.NetHandle;
import com.model.DoUongModel;
import com.utilities.CommonFont;
import com.utilities.NonBorder;
import com.utilities.RoundedButton;
import com.view.frame.LoginFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoangdp
 */
public class BillPanel extends Container {

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 0));

        lbtitle = new JLabel(TITLE + LoginFrame.ID, JLabel.CENTER);
        lbtitle.setFont(new CommonFont(Font.BOLD, 30));
        lbtitle.setForeground(Color.WHITE);
        add(lbtitle, BorderLayout.NORTH);

        tbBill = new JTable();
        dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{
            NUMBER_ORDER,
            NAME_DRINK,
            PRICE,
            QUANLITY
        });

        tbBill.setModel(dtm);

        tbBill.getTableHeader().setFont(new CommonFont(14));
        tbBill.setRowHeight(30);
        tbBill.setFont(new CommonFont(14));
        JViewport viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.setView(tbBill);
        scTb = new JScrollPane();
        scTb.setViewport(viewport);
        scTb.setOpaque(false);
        scTb.setBorder(new NonBorder());
        scTb.getViewport().setOpaque(false);
        add(scTb, BorderLayout.CENTER);

        btnPay = new RoundedButton(PAY, 100, 50, 10);
        btnPay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pay();
            }
        });

        add(btnPay, BorderLayout.SOUTH);
    }

    private void loadText() {
        TITLE = LanguageHandle.getInstance().getValue("Bill", "TITLE");
        NUMBER_ORDER = LanguageHandle.getInstance().getValue("Bill", "NUMBER_ORDER");
        NAME_DRINK = LanguageHandle.getInstance().getValue("Bill", "NAME_DRINK");
        PRICE = LanguageHandle.getInstance().getValue("Bill", "PRICE");
        QUANLITY = LanguageHandle.getInstance().getValue("Bill", "QUANLITY");
        PRINT = LanguageHandle.getInstance().getValue("Bill", "PRINT");
        PAY = LanguageHandle.getInstance().getValue("Bill", "PAY");
        PAY_NOTIFICATION = LanguageHandle.getInstance().getValue("Bill", "PAY_NOTIFICATION");
        PAY_TITLE = LanguageHandle.getInstance().getValue("Bill", "PAY_TITLE");
    }

    public void addDrinks(String ma) {
        int id = Integer.parseInt(ma.trim());
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (Integer.parseInt(dtm.getValueAt(i, 0).toString())
                    == id) {
                int value = Integer.parseInt(dtm.getValueAt(i, 3).toString());
                dtm.setValueAt(
                        ++value,
                        i,
                        3
                );
                return;
            }
        }
        DoUongModel du = DoUongController.getInstance()
                .getDoUongs()
                .stream()
                .filter(x -> x.getMaDU() == id)
                .findFirst()
                .get();
        dtm.addRow(new Object[]{
            du.getMaDU(),
            du.getTenDU(),
            du.getGia(),
            1
        });
    }

    public void removeDrinks(String ma) {
        System.out.println("huy mon");
        int id = Integer.parseInt(ma.trim());
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (Integer.parseInt(dtm.getValueAt(i, 0).toString())
                    == id) {
                int value = Integer.parseInt(dtm.getValueAt(i, 3).toString());
                if (value == 1) {
                    dtm.removeRow(i);
                } else {
                    dtm.setValueAt(
                            --value,
                            i,
                            3
                    );
                }
                return;
            }
        }
    }

    public void pay() {
        String sdt = JOptionPane.showInputDialog(
                null,
                PAY_NOTIFICATION,
                PAY_TITLE,
                JOptionPane.DEFAULT_OPTION
        );
        if (sdt == null) {
            return;
        }
        NetHandle.getInstance().sendMessages("Thanh Toan");
        NetHandle.getInstance().sendMessages("SDT: " + sdt);
    }

    public BillPanel() {
        loadText();
        initComponents();
    }

    // Components
    private JLabel lbtitle;
    private RoundedButton btnPay;
    private JTable tbBill;
    private JScrollPane scTb;

    // Varible
    DefaultTableModel dtm;
    private String content;

    // Text
    private String TITLE;
    private String NUMBER_ORDER;
    private String NAME_DRINK;
    private String PRICE;
    private String QUANLITY;
    private String PRINT;
    private String PAY;
    private String PAY_NOTIFICATION;
    private String PAY_TITLE;
}
