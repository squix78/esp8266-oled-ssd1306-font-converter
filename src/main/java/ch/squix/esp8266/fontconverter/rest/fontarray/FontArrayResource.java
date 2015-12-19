package ch.squix.esp8266.fontconverter.rest.fontarray;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.esp8266.fontconverter.rest.FontConverter;

public class FontArrayResource extends ServerResource {

    @Post(value = "json")
    public FontArrayDto execute(FontArrayDto dto) throws FontFormatException, IOException {
        StringBuilder builder = new StringBuilder();
        Font font = new Font(dto.getName(), dto.getStyle(), dto.getSize());
        FontConverter converter = new FontConverter(font);
        converter.printFontArray(builder);
        dto.setFontArray(builder.toString());
        return dto;
    }

}
