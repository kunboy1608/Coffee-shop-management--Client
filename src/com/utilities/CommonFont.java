/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utilities;

import java.awt.Font;

/**
 *
 * @author hoangdp
 */
public class CommonFont extends Font {

    private static String nameFont = "PLAIN";

    public CommonFont() {
        super(nameFont, Font.PLAIN, 12);
    }

    public CommonFont(int size) {
        super(nameFont, Font.PLAIN, size);
    }

    public CommonFont(int style, int size) {
        super(nameFont, style, size);
    }
}
