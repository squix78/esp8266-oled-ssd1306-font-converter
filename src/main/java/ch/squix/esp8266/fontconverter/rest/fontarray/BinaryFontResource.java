package ch.squix.esp8266.fontconverter.rest.fontarray;

import ch.squix.esp8266.fontconverter.rest.FontConverterV3;
import ch.squix.esp8266.fontconverter.rest.font.FontDto;
import org.restlet.data.MediaType;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.OutputRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class BinaryFontResource extends ServerResource {

    @Get()
    public void execute() throws FontFormatException, IOException {

        String fontFamily = URLDecoder.decode(
                (String) this.getRequestAttributes().get("fontName"), "utf8");
        String fontStyleInput = (String) this.getRequestAttributes().get("fontStyle");
        Integer fontStyle = 0;
        if ("plain" == fontStyleInput ) {
            fontStyle = 0;
        } else if ("bold" == fontStyleInput) {
            fontStyle = 1;
        } else if ("italic" == fontStyleInput) {
            fontStyle = 2;
        }
        String fontSizeInput = (String) this.getRequestAttributes().get("fontSize");
        Integer fontSize = Integer.valueOf(fontSizeInput);

        final Font font = new Font(fontFamily, fontStyle, fontSize);
        final FontConverterV3 converter = new FontConverterV3(font);
        ByteArrayOutputStream baos = converter.getBinaryOutputStream();
        byte[] bytes = baos.toByteArray();
        System.out.println(bytes.length);
        ByteArrayRepresentation bar = new ByteArrayRepresentation(bytes, MediaType.ALL, bytes.length);
        getResponse().setEntity(bar);

    }

}
