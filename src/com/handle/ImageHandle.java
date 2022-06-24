/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.handle;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author kunbo
 */
public class ImageHandle {

    private final static ImageHandle _instance = new ImageHandle();

    private ImageHandle() {
    }

    public static ImageHandle getInstance() {
        return _instance;
    }

    public ImageIcon resize(ImageIcon originalImage, int targetWidth, int targetHeight) {
        ImageIcon resultingImage = new ImageIcon(originalImage.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH));
        return resultingImage;
    }

    public Image resize(Image originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        return resultingImage;
    }

    public Image resize(BufferedImage originalBufferedImage, int targetWidth, int targetHeight) {
        return resize((Image) originalBufferedImage, targetWidth, targetHeight);
    }

    public byte[] toByteArray(Image img, String type) {
        try {
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g = bimage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bimage, type, baos);

            byte[] imageInByte;
            imageInByte = baos.toByteArray();

            return imageInByte;
        } catch (IOException | java.lang.NullPointerException e) {
            System.out.println("File nulll");
            return null;
        }
    }

    public Image createImageFromByteArray(byte[] data, String type) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage = ImageIO.read(bis);
            Image img = bImage.getScaledInstance(bImage.getWidth(), bImage.getHeight(), Image.SCALE_SMOOTH);
            return img;
        } catch (java.lang.NullPointerException | IOException e) {
            System.out.println("Loi tao anh");
            return null;
        }
    }

    public Image createImageFromBlob(Blob b) {
        try {
            byte[] imagebytes = b.getBytes(1, (int) b.length());
            return createImageFromByteArray(imagebytes, "jpg");
        } catch (SQLException | java.lang.NullPointerException e) {
            return null;
        }
    }

    public byte[] BlobToByteArray(Blob b) {
        try {
            int myblobLength = (int) b.length();
            byte[] myblobAsBytes = b.getBytes(1, myblobLength);
            return myblobAsBytes;
        } catch (SQLException ex) {
            Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Image readImage(String URL) {
        try {
            BufferedImage bi = ImageIO.read(getClass().getResourceAsStream(URL));
            return resize(
                    bi,
                    bi.getWidth(),
                    bi.getHeight()
            );
        } catch (IOException | java.lang.NullPointerException e) {
            System.out.println("Không timg thấy ảnh");
            return null;
        }
    }

    public ImageIcon readImageIcon(String URL) {
        try {
            return new ImageIcon(readImage(URL));

        } catch (java.lang.NullPointerException e) {
            System.out.println("Không timg thấy ảnh");
            return null;
        }
    }

    public BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage bufferedImage) {
            return bufferedImage;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public BufferedImage makeRoundedCorner(Image img, int cornerRadius) {
        BufferedImage image = getInstance().toBufferedImage(img);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)
        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();
        return output;
    }

    public BufferedImage getBufferedImage(Image img) {
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bimage;
    }

    public Image getIconLogo() {
        return readImage("/com/resource/logo.png");
    }
}
