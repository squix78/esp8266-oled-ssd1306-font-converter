package ch.squix.esp8266.fontconverter.rest.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FontFamilyResource extends ServerResource {

    @Get(value = "json")
    public List<FontFamilyDto> execute() throws FontFormatException, IOException {
        List<FontFamilyDto> fonts = new ArrayList<>();
        String[] fontFiles = {"Arial Black.ttf", "Arial Bold Italic.ttf", "Arial Bold.ttf",
                "Arial Italic.ttf"};
        for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
            FontFamilyDto dto = new FontFamilyDto();
            dto.setFontFamily(font.getFamily());
            fonts.add(dto);
        }

        // for (String fontFileName : fontFiles) {
        // Font font = Font.createFont(Font.TRUETYPE_FONT,
        // getClass().getClassLoader()
        // .getResourceAsStream("fonts/" + fontFileName));
        // FontFamilyDto dto = new FontFamilyDto();
        // dto.setFontFamily(font.getFamily());
        // fonts.add(dto);
        // }
        return fonts;
    }

}
