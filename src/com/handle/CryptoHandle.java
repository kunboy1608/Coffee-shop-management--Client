/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.handle;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kunbo
 */
public class CryptoHandle {

    public static boolean MatchingMD5(String orig, String compare) {
        try {
            String md5;

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(compare.getBytes());
            byte[] digest = md.digest();
            md5 = new BigInteger(1, digest).toString(16);

            return md5.equals(orig);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public static String EncodeMD5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
