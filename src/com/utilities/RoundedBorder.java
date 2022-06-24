/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author kunbo
 */
public class RoundedBorder extends AbstractBorder {

    private int top;
    private int bot;
    private int left;
    private int right;

    public RoundedBorder() {
        this.top = 5;
        this.left = 10;
        this.bot = 5;
        this.right = 10;

    }

    public RoundedBorder(int top, int left, int bot, int right) {
        this.top = top;
        this.bot = bot;
        this.left = left;
        this.right = right;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.WHITE);
        g.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(top, left, bot, right);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
