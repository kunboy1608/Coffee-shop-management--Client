/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.view.frame;

import com.handle.ImageHandle;
import com.handle.LanguageHandle;
import com.utilities.RoundedButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author hoangdp
 */
public class AdvertisementFrame extends JFrame {

    private void loadText() {
        LETGO = LanguageHandle.getInstance().getValue("Ad", "LETGO");
    }

    private void initComponents() {
        setIconImage(ImageHandle.getInstance().getIconLogo());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(ADVERTISEMENT_WIDTH, ADVERTISEMENT_HEIGHT));

        mainJPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawImages(g);
            }

        };
        mainJPanel.setLayout(new BorderLayout());

        slidePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSlide(g);
            }
        };
        slidePanel.setOpaque(false);
        mainJPanel.add(slidePanel, BorderLayout.CENTER);

        botCon = new Container();
        botCon.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;
        btnAction = new RoundedButton(LETGO, 300, 100, 10);
        btnAction.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getInstance().setVisible(true);
                setVisible(false);
            }
        });
        botCon.add(btnAction, gbc);

        iconLanguage = ImageHandle.getInstance().readImage("/com/resource/language.png");
        iconLanguage = ImageHandle.getInstance().resize(iconLanguage, 30, 30);
        btnLanguage = new JLabel(new ImageIcon(iconLanguage));
        btnLanguage.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ChangeLanguage();
            }
        });
        gbc.gridx = 1;
        botCon.add(btnLanguage, gbc);

        mainJPanel.add(botCon, BorderLayout.PAGE_END);
        this.add(mainJPanel);
    }

    private void ChangeLanguage() {
        LanguageHandle.getInstance().ChangeLanguage();
        this.dispose();
        _instance=new AdvertisementFrame();
        _instance.setVisible(true);
    }

    public void drawImages(Graphics g) {
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

        //De nut doi ngon ngu o goc
        btnLanguage.setLocation(
                botCon.getWidth() - btnLanguage.getWidth(),
                botCon.getHeight() - btnLanguage.getHeight()
        );
    }

    public void drawSlide(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        var x = ImageHandle.getInstance().resize(
                ImageHandle.getInstance().makeRoundedCorner(
                        listPoster.get(currentImage),
                        50),
                (int) (slidePanel.getWidth() * 0.8),
                (int) (slidePanel.getHeight() * 0.9)
        );
        g2d.drawImage(
                x,
                (int) (slidePanel.getWidth() * 0.1),
                (int) (slidePanel.getHeight() * 0.05),
                null
        );
    }

    private void loadImages() {
        imageBackground = ImageHandle.getInstance().readImage("/com/resource/background_login.jpg");
        listPoster = new LinkedList<>();
        listPoster.add(ImageHandle.getInstance().readImage("/com/resource/ad1.png"));
        listPoster.add(ImageHandle.getInstance().readImage("/com/resource/ad2.png"));
        listPoster.add(ImageHandle.getInstance().readImage("/com/resource/ad3.png"));
    }

    private AdvertisementFrame() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadText();
        loadImages();
        initComponents();
        changSlide = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(3000);
                        currentImage = (currentImage + 1) % listPoster.size();
                        repaint();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AdvertisementFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        changSlide.start();
    }

    public static synchronized AdvertisementFrame getIntance() {
        if (_instance == null) {
            _instance = new AdvertisementFrame();
        }
        return _instance;
    }

    private static AdvertisementFrame _instance;

    // UI Components
    private JPanel mainJPanel;
    private RoundedButton btnAction;
    private Image imageBackground;
    private Image iconLanguage;
    private JLabel btnLanguage;
    private JPanel slidePanel;
    private Container botCon;

    // xxx
    private LinkedList<Image> listPoster;
    private int currentImage = 0;
    private final Thread changSlide;

    // Size    
    private final int ADVERTISEMENT_HEIGHT = 844;
    private final int ADVERTISEMENT_WIDTH = 1520;

    private String LETGO;
}
