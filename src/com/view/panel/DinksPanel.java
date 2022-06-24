/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.view.panel;

import com.handle.ImageHandle;
import com.utilities.CommonFont;
import com.utilities.RoundedButton;
import com.view.frame.MainFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author hoangdp
 */
public class DinksPanel extends Container {

    public DinksPanel(int id, String name, Image img) {
        this.imageIcon = new ImageIcon(
                ImageHandle.getInstance().resize(
                        ImageHandle.getInstance()
                                .makeRoundedCorner(
                                        img,
                                        50
                                ),
                        300,
                        250
                )
        );
        this.id = id;
        setLayout(new BorderLayout(3, 3));

        lbImage = new JLabel(imageIcon);
        add(lbImage);

        Container c = new Container();
        c.setLayout(new GridLayout(2, 1, 3, 3));
        lbName = new JLabel(name);
        lbName.setFont(new CommonFont(25));
        lbName.setForeground(Color.WHITE);
        lbName.setHorizontalAlignment(JLabel.CENTER);
        c.add(lbName);
        Container conBtn = new Container();

        conBtn.setLayout(new GridLayout(1, 2, 3, 3));

        btnSub = new RoundedButton("-", 30, 30, 10);
        btnSub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getInstance().sub(id);
            }
        });
        conBtn.add(btnSub);

        btnPlus = new RoundedButton("+", 30, 30, 10);
        btnPlus.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getInstance().plus(id);
            }
        });
        conBtn.add(btnPlus);
        c.add(conBtn);
        add(c, BorderLayout.PAGE_END);
    }

    public int getId() {
        return id;
    }

    //Components
    private final ImageIcon imageIcon;
    private final JLabel lbImage;
    private final JLabel lbName;
    private final RoundedButton btnPlus;
    private final RoundedButton btnSub;

    //Variable
    private final int id;
}
