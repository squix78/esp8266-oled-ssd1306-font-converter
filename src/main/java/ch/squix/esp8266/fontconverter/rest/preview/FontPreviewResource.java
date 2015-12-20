package ch.squix.esp8266.fontconverter.rest.preview;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.restlet.data.MediaType;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FontPreviewResource extends ServerResource {

    @Get("image/png")
    public void getPreview() throws IOException {
        String fontFamilyInput = (String) this.getRequestAttributes().get("fontName");
        String fontStyleInput = (String) this.getRequestAttributes().get("fontStyle");
        Integer fontStyle = Integer.valueOf(fontStyleInput);
        String fontSizeInput = (String) this.getRequestAttributes().get("fontSize");
        Integer fontSize = Integer.valueOf(fontSizeInput) * 2;
        BufferedImage oldImage = null;

        oldImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("display.png"));
        Font font = new Font(fontFamilyInput, fontStyle, fontSize);

        BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(oldImage, 0, 0, null);
        graphics.setFont(font);
        graphics.setClip(8, 65, 128 * 2, 64 * 2);

        graphics.drawString("ABC abc 123", 8, 63 + graphics.getFontMetrics().getAscent());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        ByteArrayRepresentation bar = new ByteArrayRepresentation(bytes, MediaType.IMAGE_PNG);
        getResponse().setEntity(bar);
    }
}
