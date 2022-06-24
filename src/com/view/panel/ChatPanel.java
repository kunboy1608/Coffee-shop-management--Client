/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.view.panel;

import com.controller.DoUongController;
import com.handle.ImageHandle;
import com.handle.LanguageHandle;
import com.handle.NetHandle;
import com.model.DoUongModel;
import com.utilities.CommonFont;
import com.utilities.NonBorder;
import com.utilities.RoundedBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 *
 * @author hoangdp
 */
public class ChatPanel extends Container {

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        Messages = new JEditorPane();
        Messages.setFont(new CommonFont(14));
        Messages.setContentType("text/html");
        Messages.setEditable(false);
        Contents = """
        <style>
            .me{
                background-color: #0084ff;
                margin: 5px;
                padding: 10px;
                text-align: right;
                color: #fff;
            }
            .employee{
                background-color: #eee;
                margin: 5px;
                padding: 10px;
            }
        </style>
        <body style = "font-family: Helvetica, Arial, sans-serif; font-size: 20;">                         
                        """;

        Messages.setText(Contents);
        Messages.setOpaque(false);
        JViewport viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.setView(Messages);
        scChat = new JScrollPane();
        scChat.setViewport(viewport);
        scChat.setOpaque(false);
        scChat.setBorder(new NonBorder());
        scChat.getViewport().setOpaque(false);
        this.add(scChat, BorderLayout.CENTER);

        var c = new Container();
        c.setLayout(new BorderLayout(5, 5));

        txtChat = new JTextField();
        txtChat.setOpaque(false);
        txtChat.setFont(new CommonFont(30));
        txtChat.setForeground(Color.red);
        txtChat.setBorder(new RoundedBorder());
        txtChat.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
        c.add(txtChat, BorderLayout.CENTER);

        btnSend = new JLabel(
                ImageHandle
                        .getInstance()
                        .resize(
                                ImageHandle
                                        .getInstance()
                                        .readImageIcon("/com/resource/send.png"),
                                45,
                                45
                        ));
        btnSend.setBorder(new NonBorder());
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendMessage();
            }
        });
        btnSend.setPreferredSize(new Dimension(50, 50));
        c.add(btnSend, BorderLayout.LINE_END);

        this.add(c, BorderLayout.PAGE_END);

        this.setPreferredSize(new Dimension(WIDTH_CHAT, HEIGHT_CHAT));
    }

    private void sendMessage() {
        if (txtChat.getText().trim().equals("")) {
            return;
        }
        Contents += "<div class=\"me\">" + txtChat.getText().trim() + "</div>";
        Messages.setText(Contents);
        NetHandle.getInstance().sendMessages(txtChat.getText().trim());
        txtChat.setText("");
    }

    public void showOrder(int id) {
        DoUongModel doUongModel = DoUongController.getInstance()
                .getDoUongs()
                .stream()
                .filter(x -> x.getMaDU() == id)
                .findFirst()
                .get();
        Contents += "<div class=\"me\">" + ORDER + doUongModel.getTenDU() + "</div>";
        Messages.setText(Contents);
    }

    public void showCancel(int id) {
        DoUongModel doUongModel = DoUongController.getInstance()
                .getDoUongs()
                .stream()
                .filter(x -> x.getMaDU() == id)
                .findFirst()
                .get();
        Contents += "<div class=\"me\">" + CANCEL + doUongModel.getTenDU() + "</div>";
        Messages.setText(Contents);
    }

    public void receiveMessage(String message) {
        if (message == null) {
            return;
        }
        Contents += "<div class=\"employee\">" + message + "</div>";
        Messages.setText(Contents);
    }

    private void loadText() {
        HELLO = LanguageHandle.getInstance().getValue("Chat", "HELLO");
        ORDER = LanguageHandle.getInstance().getValue("Chat", "ORDER");
        CANCEL = LanguageHandle.getInstance().getValue("Chat", "CANCEL");
    }

    public ChatPanel() {
        initComponents();
        loadText();
        receiveMessage(HELLO);
    }

    // Components
    private JEditorPane Messages;
    private JScrollPane scChat;
    private JLabel btnSend;
    private JTextField txtChat;

    // Variables
    private String Contents;
    private final int WIDTH_CHAT = 500;
    private final int HEIGHT_CHAT = 300;

    //Text
    private String HELLO;
    private String ORDER;
    private String CANCEL;
    
}
