package ch.squix.esp8266.fontconverter.rest.fontarray;

import ch.squix.esp8266.fontconverter.rest.FontConverterV3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class BinaryFontResource {

    @GetMapping("/rest/binaryFont/{fontName}/{fontStyle}/{fontSize}")
    public byte[] execute(@PathVariable String fontName, 
        @PathVariable String fontStyle,
        @RequestParam Integer fontSize) throws FontFormatException, IOException {
        Integer fontStyleIndex = 0;
        if ("plain" == fontStyle ) {
            fontStyleIndex = 0;
        } else if ("bold" == fontStyle) {
            fontStyleIndex = 1;
        } else if ("italic" == fontStyle) {
            fontStyleIndex = 2;
        }

        final Font font = new Font(fontName, fontStyleIndex, fontSize);
        final FontConverterV3 converter = new FontConverterV3(font);
        ByteArrayOutputStream baos = converter.getBinaryOutputStream();
        byte[] bytes = baos.toByteArray();
        System.out.println(bytes.length);
        return bytes;
    }

}
