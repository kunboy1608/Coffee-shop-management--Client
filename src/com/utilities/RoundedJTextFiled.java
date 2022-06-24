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
import javax.swing.JTextField;

/**
 *
 * @author hoangdp
 */
public class RoundedJTextFiled extends JTextField {

    private String content;
    private int width;
    private int height;
    private Color color;
    private int radius;

    private void initProperties() {
        setPreferredSize(new Dimension(width, height));
        setText(content);

        color = getForeground();

        setSelectedTextColor(Color.BLACK);
        setFont(new CommonFont(Font.BOLD, 30));

        setOpaque(false);
        setBorder(new NonBorder(5, radius / 2, 5, radius / 2));
    }

    public RoundedJTextFiled() {
        this.content = "";
        this.width = 100;
        this.height = 70;
        initProperties();
    }

    public RoundedJTextFiled(String content, int width, int height, int radius) {
        this.content = content;
        this.width = width;
        this.height = height;
        this.radius = radius;
        initProperties();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(1f, 1f, 1f, 0.8f));
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g);
    }
}
