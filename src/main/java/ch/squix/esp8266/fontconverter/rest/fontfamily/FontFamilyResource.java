package ch.squix.esp8266.fontconverter.rest.fontfamily;

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
        GraphicsEnvironment graphicEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String fontFamilyName : graphicEnvironment.getAvailableFontFamilyNames()) {
            FontFamilyDto dto = new FontFamilyDto();
            dto.setName(fontFamilyName);
            fonts.add(dto);
        }
        return fonts;
    }

}
