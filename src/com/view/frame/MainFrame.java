/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.view.frame;

import com.controller.DoUongController;
import com.handle.ImageHandle;
import com.handle.LanguageHandle;
import com.handle.NetHandle;
import com.model.DoUongModel;
import com.utilities.NonBorder;
import com.view.panel.BillPanel;
import com.view.panel.ChatPanel;
import com.view.panel.DinksPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Hashtable;
import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author hoangdp
 */
public class MainFrame extends JFrame {

    private void initChatArea() {
        leftCon = new Container();
        leftCon.setLayout(new BorderLayout(5, 5));
        leftCon.setPreferredSize(new Dimension(400, 0));
        chatPanel = new ChatPanel();
        leftCon.add(chatPanel);
        panel.add(leftCon, BorderLayout.LINE_START);
    }

    private void initDrinkArea() {
        midCon = new Container();
        midCon.setLayout(new GridLayout());
        pMain = new Container();
        pMain.setLayout(new GridLayout(0, 3, 30, 30));
        loadDrinks();
        JViewport viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.setView(pMain);
        scMain = new JScrollPane();
        scMain.setViewport(viewport);
        scMain.setOpaque(false);
        scMain.setBorder(new NonBorder());
        scMain.getViewport().setOpaque(false);
        scMain.getVerticalScrollBar().setUnitIncrement(50);
        scMain.setPreferredSize(new Dimension(300, 300));
        midCon.add(scMain);
        panel.add(midCon, BorderLayout.CENTER);
    }

    private void initBillArea() {
        rightCon = new Container();
        rightCon.setLayout(new GridLayout());
        rightCon.setPreferredSize(new Dimension(400, 0));
        billPanel = new BillPanel();
        rightCon.add(billPanel);
        panel.add(rightCon, BorderLayout.LINE_END);
    }

    private void initComponents() {
        setIconImage(ImageHandle.getInstance().getIconLogo());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setTitle(TITLE);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBackground(g);
            }
        };
        panel.setLayout(new BorderLayout());
        add(panel);
        setMinimumSize(new Dimension(1500, 700));
        initChatArea();
        initDrinkArea();
        initBillArea();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void drawBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Ve hinh nen
        g2d.drawImage(
                ImageHandle.getInstance().resize(imageBackground, getWidth(), getHeight()),
                0,
                0,
                null
        );
        // Lam mo hinh nen
        g2d.setColor(new Color(0f, 0f, 0f, 0.6f));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public void addDrinks(DoUongModel du) {
        pMain.add(new DinksPanel(
                du.getMaDU(),
                du.getTenDU(),
                du.getHinhAnh()
        ));
    }

    private void loadText() {
        TITLE = LanguageHandle.getInstance().getValue("Menu", "TITLE");
        TABLE = LanguageHandle.getInstance().getValue("Menu", "TABLE");
    }

    public void sub(int id) {
        NetHandle.getInstance().sendMessages("HuyMon:  " + id);
        chatPanel.showCancel(id);
    }

    public void plus(int id) {
        NetHandle.getInstance().sendMessages("ThemMon: " + id);
        chatPanel.showOrder(id);
    }

    private void loadDrinks() {
        DoUongController.getInstance().LayDuLieu();
        for (DoUongModel x : DoUongController.getInstance().getDoUongs()) {
            addDrinks(x);
        }
    }

    public void receiveMessage(String message) {
        System.out.println(message);
        if (message.contains("ThemMon: ")) {
            billPanel.addDrinks(message.substring(9));
        } else if (message.contains("HuyMon:  ")) {
            billPanel.removeDrinks(message.substring(9));
        } else {
            chatPanel.receiveMessage(message);
        }
    }

    public void resetTable() {
        chatPanel = new ChatPanel();
        leftCon.removeAll();
        leftCon.add(chatPanel);

        billPanel = new BillPanel();
        rightCon.removeAll();
        rightCon.add(billPanel);

        setVisible(false);
        AdvertisementFrame.getIntance().setVisible(true);
    }

    private MainFrame() {
        loadText();
        initComponents();
        imageBackground = ImageHandle.getInstance().readImage("/com/resource/background_menu.jpg");
    }

    private void changLanguage() {
        loadText();
        initComponents();
        validate();
        repaint();
    }

    public static synchronized MainFrame getInstance() {
        if (_instance == null) {
            _instance = new MainFrame();
        }
        return _instance;
    }

    private static MainFrame _instance;
    // GUI Components
    private Container leftCon;
    private Container midCon;
    private Container rightCon;
    private Container botCon;
    private JScrollPane scMain;
    private JScrollPane scChat;
    private JPanel panel;
    private Container pMain;
    private Container pBill;
    private JEditorPane txtChat;
    private ChatPanel chatPanel;
    private BillPanel billPanel;

    // Variable
    private Hashtable<String, Object[]> list;
    private ButtonGroup group;
    private String currentTable;
    private final Image imageBackground;

    //Text 
    private String TITLE;
    private String TABLE;
}
