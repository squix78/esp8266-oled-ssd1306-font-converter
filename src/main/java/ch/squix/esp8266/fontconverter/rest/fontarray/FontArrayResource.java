package ch.squix.esp8266.fontconverter.rest.fontarray;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.esp8266.fontconverter.rest.EmailNotificator;
import ch.squix.esp8266.fontconverter.rest.FontConverterV2;
import ch.squix.esp8266.fontconverter.rest.FontConverterV3;


public class FontArrayResource extends ServerResource {

    @Post(value = "json")
    public FontArrayDto execute(FontArrayDto dto) throws FontFormatException, IOException {
        StringBuilder builder = new StringBuilder();
        Font font = new Font(dto.getName(), Integer.valueOf(dto.getStyle()), dto.getSize());
        if ("2".equals(dto.getLibVersion())) {
            FontConverterV2 converter = new FontConverterV2(font);
            converter.printFontArray(builder);
        } else if ("3".equals(dto.getLibVersion())) {
            FontConverterV3 converter = new FontConverterV3(font);
            converter.printLetterData(builder);
        }
        dto.setFontArray(builder.toString());
        sendEmail(dto);
        return dto;
    }

    void sendEmail(FontArrayDto dto) {
        String body = "Font: " + dto.getName() + "\nSize: " + dto.getSize() + "\nStyle: "
                + dto.getStyle() + "\nLib Version: " + dto.getLibVersion();
        EmailNotificator.sendEmail("dani.eichhorn@squix.ch", "dani.eichhorn@squix.ch",
                "New Font Created", body);

    }
}
