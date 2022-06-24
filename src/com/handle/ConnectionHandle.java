/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.handle;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author kunbo
 */
public class ConnectionHandle {

    private static final ConnectionHandle _instance = new ConnectionHandle();
    private Connection con;
    private final String[] sInfo;
    private final String[] user;

    private ConnectionHandle() {
        sInfo = ConfigurationLoader.getInstance().getServerInfo();
        user = ConfigurationLoader.getInstance().getUserInfo();
    }

    private void Connect() {
        try {
            String connectionUrl
                    = "jdbc:sqlserver://" + sInfo[0] + ":" + sInfo[2] + ";"
                    + "database=" + sInfo[1] + ";"
                    + "user=" + user[0] + ";"
                    + "password=" + user[1] + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
            System.out.println(user[0] + user[1]);
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ConnectManager() {
        Connect();
    }

    public Connection getConnection() {
        if (con == null) {
            Connect();
        }
        return con;
    }

    public static ConnectionHandle getInstance() {
        return _instance;
    }
}
