package ch.squix.esp8266.fontconverter.rest;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FontRepository {

    private static Map<String, String> fontMap = new HashMap<>();

    static {

    }

    public static List<String> getFontNames() throws URISyntaxException {
        List<String> fontNames = new ArrayList<>();
        URL url = FontRepository.class.getResource("fonts/");
        if (url == null) {
            // error - missing folder
        } else {
            File dir = new File(url.toURI());
            for (File nextFile : dir.listFiles()) {
                fontNames.add(nextFile.getName());
            }
        }
        return fontNames;
    }

}
