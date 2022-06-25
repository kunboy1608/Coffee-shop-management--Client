/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import com.handle.ImageHandle;
import java.awt.Image;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dang
 */
public class DoUongModel implements Serializable {

    private int MaDU;
    private String TenDU;
    private double Gia;
    private Blob HinhAnh;
    private String GhiChu;

    public DoUongModel() {
    }

    public DoUongModel(int MaDU, String TenDU, double Gia, Blob HinhAnh, String GhiChu) {
        this.MaDU = MaDU;
        this.TenDU = TenDU.trim();
        this.Gia = Gia;
        this.HinhAnh = HinhAnh;
        this.GhiChu = GhiChu.trim();
    }

    public int getMaDU() {
        return MaDU;
    }

    public void setMaDU(int MaDU) {
        this.MaDU = MaDU;
    }

    public String getTenDU() {
        return TenDU;
    }

    public void setTenDU(String TenDU) {
        this.TenDU = TenDU.trim();
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }

    public Image getHinhAnh() {
        return ImageHandle.getInstance().createImageFromBlob(HinhAnh);
    }

    public void setHinhAnh(Blob HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public void setHinhAnh(Image img) {
        try {
            this.HinhAnh.setBytes(
                    ImageHandle.getInstance().toByteArray(img, "jpg").length,
                    ImageHandle.getInstance().toByteArray(img, "jpg")
            );
        } catch (SQLException ex) {
            Logger.getLogger(DoUongModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu.trim();
    }

}
