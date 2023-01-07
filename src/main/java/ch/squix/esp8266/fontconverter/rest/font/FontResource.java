package ch.squix.esp8266.fontconverter.rest.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FontResource {



    @GetMapping("/rest/fonts")
    public List<FontDto> execute(@RequestParam(required = false) String fontFamily) throws FontFormatException, IOException {

        log.info("loading font types for {}", fontFamily);
        List<FontDto> fonts = new ArrayList<>();
        GraphicsEnvironment graphicEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : graphicEnvironment.getAllFonts()) {
            if (fontFamily == null || fontFamily.equals(font.getFamily())) {
                FontDto dto = new FontDto();
                dto.setName(font.getFontName());
                dto.setFontFamily(font.getFamily());
                dto.setPlain(font.isPlain());
                dto.setItalic(font.isItalic());
                dto.setBold(font.isBold());
                fonts.add(dto);
            }
        }
        return fonts;
    }

}
