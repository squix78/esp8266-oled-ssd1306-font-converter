package ch.squix.esp8266.fontconverter.rest.fontfamily;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FontFamilyResource {

    @GetMapping("/rest/fontFamilies")
    public List<FontFamilyDto> execute() throws FontFormatException, IOException {
        log.info("Load all font family names");
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
