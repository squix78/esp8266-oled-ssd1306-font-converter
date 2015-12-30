package ch.squix.esp8266.fontconverter.rest.xbm.preview;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import javax.imageio.ImageIO;

import org.restlet.data.MediaType;
import org.restlet.engine.util.StringUtils;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.esp8266.fontconverter.rest.xbm.BlackAndWhiteConverter;
import ch.squix.esp8266.fontconverter.rest.xbm.BlackAndWhiteConverter.C3;

public class XbmPreviewResource extends ServerResource {

    @Get("image/png")
    public void getPreview() throws IOException {
        String urlParam = (String) this.getQuery().getValues("url");
        String ditherParam = (String) this.getQuery().getValues("dither");
        String scaleParam = (String) this.getQuery().getValues("scale");
        if (StringUtils.isNullOrEmpty(urlParam)) {
            return;
        }
        double scale = 1.0;
        if (!StringUtils.isNullOrEmpty(scaleParam)) {
            scale = Double.parseDouble(scaleParam);
        }
        Boolean isDitherEnabled = true;
        if (!StringUtils.isNullOrEmpty(ditherParam)) {
            isDitherEnabled = Boolean.valueOf(ditherParam);
        }
        String url = URLDecoder.decode(urlParam, "utf8");
        BufferedImage oldImage = null;

        oldImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("display.png"));

        BufferedImage newImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(oldImage, 0, 0, null);
        graphics.setClip(5, 59, 128 * 2, 64 * 2);

        BufferedImage input = ImageIO.read(new URL(url));
        C3[] palette = new C3[]{new C3(0, 0, 0), // black
                new C3(255, 255, 255) // white
        };
        if (scale != 1.0) {

        }

        if (isDitherEnabled) {
            input = BlackAndWhiteConverter.floydSteinbergDithering(input, palette);
        } else {
            input = BlackAndWhiteConverter.thresholdConverter(input);
        }
        graphics.drawImage(input, 5, 59, input.getWidth() * 2, input.getHeight() * 2, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();
        ByteArrayRepresentation bar = new ByteArrayRepresentation(bytes, MediaType.IMAGE_PNG);
        getResponse().setEntity(bar);
    }

    public static BufferedImage getScaledImage(BufferedImage image, double scale)
            throws IOException {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();


        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform,
                AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(image,
                new BufferedImage(imageWidth, imageHeight, image.getType()));
    }

}
