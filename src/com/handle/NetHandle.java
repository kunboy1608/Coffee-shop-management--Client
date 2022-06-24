/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.handle;

import com.view.frame.LoginFrame;
import com.view.frame.MainFrame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kunbo
 */
public class NetHandle extends Thread {

    private static NetHandle _instance;
    private int port;
    private DatagramSocket socket;
    private InetAddress inetAddress;

    private NetHandle() {
        this.port = Integer.parseInt(ConfigurationLoader.getInstance().getHostInfo()[1]);
        try {
            this.socket = new DatagramSocket();
            inetAddress = InetAddress.getByName(ConfigurationLoader.getInstance().getHostInfo()[0]);
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(NetHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized NetHandle getInstance() {
        if (_instance == null) {
            _instance = new NetHandle();
            _instance.start();
        }
        return _instance;
    }

    public void sendMessages(String content) {
        DatagramPacket packet = new DatagramPacket(
                content.getBytes(),
                content.getBytes().length,
                inetAddress,
                port
        );
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
                Process(packet.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void Process(byte[] data) {
        String content = new String(data);
        if (content.contains("OK")) {
            System.out.println("Ket Noi Thanh Cong");
            LoginFrame.getInstance().LoginSuccess();
        } else if (content.trim().equalsIgnoreCase("reset")) {
            MainFrame.getInstance().resetTable();
        } else {
            MainFrame.getInstance().receiveMessage(content);
        }
    }
}
