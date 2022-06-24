/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

/**
 *
 * @author hoangdp
 */
public class RoundedButton extends JLabel {

    private String content;
    private int width;
    private int height;
    private Color currentColor;
    private Color defaultColor;
    private Color tranfColor;
    private int radius;

    private void initProperties() {
        setPreferredSize(new Dimension(width, height));
        setText(content);

        setFont(new CommonFont(Font.BOLD, 30));
        setOpaque(false);

        setHorizontalAlignment(CENTER);

        tranfColor = new Color(1f, 1f, 1f, 0.5f);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentColor = tranfColor;
                paintComponent(getGraphics());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentColor = defaultColor;
                paintComponent(getGraphics());
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    public RoundedButton() {
        this.content = "";
        this.width = 100;
        this.height = 70;
        initProperties();
    }

    public RoundedButton(String content, int width, int height, int radius) {
        this.content = content;
        this.width = width;
        this.height = height;
        this.radius = radius;
        defaultColor = new Color(0f, 1f, 0f, 1f);
        currentColor = defaultColor;
        initProperties();
    }

    public RoundedButton(String content, int width, int height, int radius, Color color) {
        this.content = content;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.defaultColor = color;
        currentColor = defaultColor;
        initProperties();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(currentColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
//        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g);
    }
}
