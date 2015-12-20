package ch.squix.esp8266.fontconverter.rest;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class FontRepository {

    public static void registerResourceFonts() throws URISyntaxException {
        List<File> fontNames = new ArrayList<>();
        File dir = new File("/var/lib/openshift/566d81410c1e6676960001c5/app-root/data/fonts/");
        parseFontNames(fontNames, dir);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (File file : fontNames) {
            System.out.println(file.getName());
            try {
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
            } catch (IOException | FontFormatException e) {
                // Handle exception
            }
        }
    }

    public static void parseFontNames(List<File> fontFiles, File folder) throws URISyntaxException {
        if (!folder.exists() || !folder.isDirectory()) {
            return;
        }

        for (File nextFile : folder.listFiles()) {
            if (nextFile.isDirectory()) {
                parseFontNames(fontFiles, nextFile);
            } else if (nextFile.getName().matches(".*ttf")) {
                fontFiles.add(nextFile);

            }
        }


    }

    public static void main(String[] args) throws URISyntaxException {
        FontRepository.registerResourceFonts();
    }

}
