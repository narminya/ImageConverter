package org.example;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws JRException {

        try {
            generateImage();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



        public static File generateImage() throws FileNotFoundException, JRException {
            File file = ResourceUtils.getFile("classpath:f.jrxml");
            try {
                if (file.exists()) {
                    List<String> data = new ArrayList<>();
                    data.add("hello");
                    data.add("bye");

                    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
                    Map<String, Object> parameters = new HashMap<>();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                    File out = new File("offer.jpg");
                    BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, 1f);
                    ImageIO.write(image, "jpg", out);
                    return out;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }




}

