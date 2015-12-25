package ch.squix.esp8266.fontconverter.rest.xbm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import ch.squix.esp8266.fontconverter.rest.xbm.FloyedSteinbergDither.C3;


public class XbmConverter {

    public static void convertImageToXbm(BufferedImage source,
            String imageName,
            StringBuilder builder) {
        Graphics sourceGraphics = source.getGraphics();
        try {
            C3[] palette = new C3[]{new C3(0, 0, 0), // black
                    new C3(255, 255, 255) // white
            };
            FloyedSteinbergDither dither = new FloyedSteinbergDither();
            BufferedImage target = FloyedSteinbergDither.floydSteinbergDithering(source, palette);
            // BufferedImage target = source;
            int width = source.getWidth();
            width = width % 8 == 0 ? width : ((width / 8) + 1) * 8;
            int height = source.getHeight();

            Color color;
            byte outputData[] = new byte[width * height / 8];
            int bitNum = 0;
            for (int y = 0; y < source.getHeight(); y++) {
                for (int x = 0; x < source.getWidth(); x++) {
                    bitNum = x + y * width;
                    int byteNum = bitNum / 8;
                    int bitPos = bitNum % 8;
                    color = new Color(target.getRGB(x, y));
                    int grayValue = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
                    if (grayValue < 127) {
                        outputData[byteNum] |= 1 << bitPos;
                    } else {
                        outputData[byteNum] &= ~(1 << bitPos);
                    }

                }
            }
            builder.append("#define " + imageName + "_width " + source.getWidth() + "\n");
            builder.append("#define " + imageName + "_height " + source.getHeight() + "\n");
            builder.append("const char " + imageName + "_bits[] = {\n");
            for (int i = 0; i < outputData.length; i++) {
                builder.append(String.format("0x%02X", outputData[i]));
                if (i < outputData.length - 1) {
                    builder.append(", ");
                }
                if (i % 16 == 0) {
                    builder.append("\n");
                }
            }
            builder.append("\n};");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        StringBuilder builder = new StringBuilder();
        final BufferedImage img = ImageIO.read(
                new URL("https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png")).getSubimage(
                228, 228, 128, 64);

        XbmConverter.convertImageToXbm(img, "Test", builder);
        System.out.println(builder.toString());
    }

}
