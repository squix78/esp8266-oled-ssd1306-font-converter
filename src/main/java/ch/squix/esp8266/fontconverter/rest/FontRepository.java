package ch.squix.esp8266.fontconverter.rest;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


public class FontRepository {

    public static void registerResourceFonts() throws URISyntaxException, IOException, FontFormatException {
        List<File> fontNames = new ArrayList<>();
        //File dir = new File(FontRepository.class.getClassLoader().getResource("fonts").toURI());
        File dir = new File(FontRepository.class.getResource("/fonts").toExternalForm());
        System.out.println("Font dir: " + dir);
        listFontFiles();
        parseFontNames(fontNames, dir);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    }

    public static void parseFontNames(List<File> fontFiles, File folder) throws URISyntaxException {
        System.out.println("Folder " + folder + " exists: " + folder.exists() + ", isDirectory: " + folder.isDirectory());
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder not found: " + folder);
            return;
        }

        for (File nextFile : folder.listFiles()) {
            if (nextFile.isDirectory()) {
                System.out.println("Dir: " + nextFile);
                parseFontNames(fontFiles, nextFile);
            } else if (nextFile.getName().matches(".*ttf")) {
                System.out.println("Font found: " + nextFile);
                fontFiles.add(nextFile);

            }
        }


    }

    public static List<String> listFontFiles() throws URISyntaxException, IOException, FontFormatException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        URI uri = FontRepository.class.getResource("/apache").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/apache");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 10);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            Path path = it.next();
            if (path.toString().endsWith("ttf")) {
                System.out.println(path);
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, path.toFile()));
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) throws URISyntaxException, IOException, FontFormatException {
        FontRepository.registerResourceFonts();
    }

}
