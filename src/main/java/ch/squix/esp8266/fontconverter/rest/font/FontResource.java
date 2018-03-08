package ch.squix.esp8266.fontconverter.rest.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FontResource extends ServerResource {

    @Get(value = "json")
    public List<FontDto> execute() throws FontFormatException, IOException {
        String fontFamily = (String) this.getRequestAttributes().get("fontFamily");
        List<FontDto> fonts = new ArrayList<>();
        GraphicsEnvironment graphicEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : graphicEnvironment.getAllFonts()) {
            if (fontFamily == null || fontFamily.equals(font.getFamily())) {
                FontDto dto = new FontDto();
                dto.setName("YY" + font.getFontName());
                dto.setFontFamily("XX" + font.getFamily());
                dto.setPlain(font.isPlain());
                dto.setItalic(font.isItalic());
                dto.setBold(font.isBold());
                fonts.add(dto);
            }
        }
        return fonts;
    }

}
