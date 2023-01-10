package ch.squix.esp8266.fontconverter.rest.fontarray;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.squix.esp8266.fontconverter.rest.FontConverterGFX;
import ch.squix.esp8266.fontconverter.rest.FontConverterV2;
import ch.squix.esp8266.fontconverter.rest.FontConverterV3;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FontArrayResource {

    @PostMapping("/rest/fontArray")
    public FontArrayDto execute(@RequestBody FontArrayDto dto) throws FontFormatException, IOException {
        log.info("Font Array Dto: {}", dto);
        StringBuilder builder = new StringBuilder();
        Font font = new Font(dto.getName(), Integer.valueOf(dto.getStyle()), dto.getSize());
        if ("2".equals(dto.getLibVersion())) {
            FontConverterV2 converter = new FontConverterV2(font);
            converter.printFontArray(builder);
            dto.setFileName(converter.getFilename() + ".h");
        } else if ("3".equals(dto.getLibVersion())) {
            FontConverterV3 converter = new FontConverterV3(font);
            converter.printLetterData(builder);
            dto.setFileName(converter.getFilename() + ".h");
        } else if ("gfx".equals(dto.getLibVersion())) {
            FontConverterGFX converter = new FontConverterGFX(font);
            converter.printLetterData(builder);
            dto.setFileName(converter.getFilename() + ".h");
        }
        dto.setFontArray(builder.toString());
        sendEmail(dto);
        return dto;
    }

    void sendEmail(FontArrayDto dto) {
        // String body = "Font: " + dto.getName() + "\nSize: " + dto.getSize() + "\nStyle: "
        //         + dto.getStyle() + "\nLib Version: " + dto.getLibVersion();
        // EmailNotificator.sendEmail("dani.eichhorn@squix.ch", "dani.eichhorn@squix.ch",
        //         "New Font Created", body);

    }
}
