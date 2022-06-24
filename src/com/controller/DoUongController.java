/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.handle.ConnectionHandle;
import com.model.DoUongModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author hai dang
 */
public class DoUongController {

    private static DoUongController _instance;
    private LinkedList<DoUongModel> list;

    public static DoUongController getInstance() {
        if (_instance == null) {
            synchronized (DoUongController.class) {
                if (_instance == null) {
                    _instance = new DoUongController();
                }
            }
        }
        return _instance;
    }

    private DoUongController() {
    }

    public void LayDuLieu() {
        try {
            String sql = "Select *  FROM DoUong";
            Statement ps = ConnectionHandle.getInstance().getConnection().createStatement();
            ResultSet rs = ps.executeQuery(sql);

            list = new LinkedList<>();
            DoUongModel du;

            while (rs.next()) {
                du = new DoUongModel();
                du.setMaDU(rs.getInt(1));
                du.setTenDU(rs.getString(2));
                du.setGia(rs.getDouble(3));
                du.setHinhAnh(rs.getBlob(4));
                du.setGhiChu(rs.getString(5));
                list.add(du);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<DoUongModel> getDoUongs() {
        return list;
    }    
}
