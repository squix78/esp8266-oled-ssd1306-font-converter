package ch.squix.esp8266.fontconverter.rest.font;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deichhor on 03.12.17.
 */
public class FontRepository {

    private List<Font> fonts = new ArrayList<Font>();
    private List<String> fontFiles = new ArrayList<>();


    public List<Font> getFonts() {
        return fonts;
    }

}
