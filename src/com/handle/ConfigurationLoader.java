/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.handle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author kunbo
 */
public class ConfigurationLoader {

    private static ConfigurationLoader _instance;
    private final String oriConfig = "/com/config/config.xml";
    private String urlConfig;

    private Document doc;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder docBuilder;

    private ConfigurationLoader() {
        this.dbf = DocumentBuilderFactory.newInstance();
        String userHomeDir = System.getProperty("user.home");
        userHomeDir += "/.coffeExpressClient/config.xml";
        urlConfig = userHomeDir;
        try {
            this.docBuilder = dbf.newDocumentBuilder();
            // Doc File            
            doc = docBuilder.parse(new File(urlConfig));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            try {
                exportFileXML();
                doc = docBuilder.parse(new File(urlConfig));
                doc.getDocumentElement().normalize();
            } catch (SAXException | IOException ex1) {
                Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void refresh() {
        _instance = new ConfigurationLoader();
    }

    public Document getLanguageConfiguration() {
        try {
            // Lay ngon ngu duoc chon hien tai
            String selection = doc.getElementsByTagName("language").item(1).getTextContent();

            // Tim link file ngon ngu
            for (int i = 2; i < doc.getElementsByTagName("language").getLength(); i++) {
                Element e = (Element) doc.getElementsByTagName("language").item(i);
                if (e.getAttribute("id").equals(selection)) {

                    // Kiem tra tham chieu hay tham tri
                    Document parse = docBuilder.parse(
                            getClass().getResourceAsStream(
                                    doc.getElementsByTagName("language").item(i).getTextContent()
                            ));
                    return parse;
                }
            }

        } catch (IOException | DOMException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getServerInfo() {
        String[] info = new String[3];
        info[0] = doc.getElementsByTagName("connection").item(1).getTextContent();
        info[1] = doc.getElementsByTagName("connection").item(2).getTextContent();
        info[2] = doc.getElementsByTagName("connection").item(3).getTextContent();
        return info;
    }

    public String[] getHostInfo() {
        String[] info = new String[2];
        info[0] = doc.getElementsByTagName("host").item(1).getTextContent();
        info[1] = doc.getElementsByTagName("host").item(2).getTextContent();
        return info;
    }

    public static synchronized ConfigurationLoader getInstance() {
        if (_instance == null) {
            _instance = new ConfigurationLoader();
        }
        return _instance;
    }

    public void exportFileXML() {
        try {
            File file = new File(urlConfig);
            file.getParentFile().mkdir();
            file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file, false);
            int read;

            byte[] bytes = new byte[8192];
            InputStream inputStream = getClass().getResourceAsStream(oriConfig);

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            // Giai phong bo nho
            InputStream.nullInputStream();
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changLanguage() {
        try {
            var e = (NodeList) doc.getElementsByTagName("language").item(1);
            if (e.item(0).getTextContent().equals("vi-vn")) {
                e.item(0).setTextContent("en-us");
            } else {
                e.item(0).setTextContent("vi-vn");
            }

            DOMSource source = new DOMSource(doc);
            FileWriter writer = new FileWriter(new File(urlConfig));
            StreamResult result = new StreamResult(writer);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);
        } catch (TransformerException | IOException ex) {
            Logger.getLogger(ConfigurationLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] getUserInfo() {
        String[] str = new String[2];

        Element nl = (Element) doc.getElementsByTagName("user").item(0);
        str[0] = nl.getElementsByTagName("username").item(0).getTextContent();
        str[1] = CryptoHandle.EncodeMD5(nl.getElementsByTagName("password").item(0).getTextContent());

        return str;
    }
}
