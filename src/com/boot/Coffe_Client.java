/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.boot;

import com.view.frame.LoginFrame;

/**
 *
 * @author kunbo
 */
public class Coffe_Client {

    /**
     * @param args the command line arguments
     */
    private void configSystem() {
        if (System.getProperty("os.name").contains("Windows")) {
            System.setProperty("sun.java2d.uiScale", "1.0");
            System.setProperty("-Dprism.allowhidpi", "false");
            System.out.println("Configure Succefully");
        }
    }

    public Coffe_Client() {
        configSystem();
        LoginFrame.getInstance().setVisible(true);
    }

    public static void main(String[] args) {
        new Coffe_Client();
    }

}
