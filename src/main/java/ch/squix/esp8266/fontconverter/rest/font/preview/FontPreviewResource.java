package ch.squix.esp8266.fontconverter.rest.font.preview;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FontPreviewResource {

    @GetMapping(value = "/rest/fontPreview/{fontFamilyParam}/{fontStyleParam}/{fontSizeParam}/{previewDisplayParam}",  produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPreviewByPath(
        @PathVariable String fontFamilyParam, 
        @PathVariable Integer fontStyleParam, 
        @PathVariable Integer fontSizeParam, 
        @PathVariable String previewDisplayParam) throws IOException {
;
        log.info("fontFamily: {}, previewDisplay: {}, fontSize: {}, fontStyle: {}",
            fontFamilyParam,  previewDisplayParam, fontSizeParam, fontStyleParam);

        PreviewDisplay previewDisplay = PreviewDisplay.valueOf(previewDisplayParam);

        System.out.println("preview Display: " + previewDisplay);
        Integer fontSize = fontSizeParam * previewDisplay.getZoomFactor();
        BufferedImage oldImage = null;

        oldImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(previewDisplay.getFilename()));
        Font font = new Font(fontFamilyParam, fontStyleParam, fontSize);

        BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(oldImage, 0, 0, null);
        graphics.setFont(font);
        Rectangle clipRect = previewDisplay.getClipRect();
        graphics.setClip(clipRect.x, clipRect.y, clipRect.width, clipRect.height);

        drawString(graphics, "ABC abc 123 $€°. The quick brown fox jumps over the lazy dog.", clipRect.x,
                clipRect.y + graphics.getFontMetrics().getAscent(), clipRect.width);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        return baos.toByteArray();

    }

    public void drawString(Graphics g, String s, int x, int y, int width) {
        // FontMetrics gives us information about the width,
        // height, etc. of the current Graphics object's Font.
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();

        int curX = x;
        int curY = y;

        String[] words = s.split(" ");

        for (String word : words) {
            // Find out thw width of the word.
            int wordWidth = fm.stringWidth(word + " ");

            // If text exceeds the width, then move to next line.
            if (curX + wordWidth >= x + width) {
                curY += lineHeight;
                curX = x;
            }

            g.drawString(word, curX, curY);

            // Move over to the right for next word.
            curX += wordWidth;
        }
    }
}
