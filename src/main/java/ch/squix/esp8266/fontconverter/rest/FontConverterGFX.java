package ch.squix.esp8266.fontconverter.rest;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * https://learn.adafruit.com/adafruit-gfx-graphics-library/using-fonts
 * https://github
 * .com/adafruit/Adafruit-GFX-Library/blob/master/fontconvert/fontconvert.c
 * https ://github.com/adafruit/Adafruit-GFX-Library/blob/master/Fonts/
 * FreeMono18pt7b .h
 * 
 * table[j].bitmapOffset = bitmapOffset; 
 * table[j].width = bitmap->width;
 * table[j].height = bitmap->rows; 
 * table[j].xAdvance = face->glyph->advance.x >> 6; 
 * table[j].xOffset = g->left; 
 * table[j].yOffset = 1 - g->top;
 * 
 * bitmapOffset += (bitmap->width * bitmap->rows + 7) / 8;
 */
public class FontConverterGFX {

    private static final int END_CHAR = 126;
    private static final int START_CHAR = 32;

    private Graphics2D g;
    private FontMetrics fontMetrics;
    private BufferedImage image;
    private int baselineY;


    public FontConverterGFX(Font font) {
        int imageSize = 100; //Math.max(100, font.getSize() * 2);
        image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setFont(font);
        fontMetrics = g.getFontMetrics();
        baselineY = fontMetrics.getMaxAscent();

    }

    public Rectangle getBoundingBox(char c) {
        FontRenderContext frc = g.getFontRenderContext();
        GlyphVector gv = g.getFont().createGlyphVector(frc, String.valueOf(c));
        return gv.getPixelBounds(null, 0, 0);
    }


    public static void main(String[] args) throws InterruptedException, IOException {

        
         GraphicsEnvironment graphicEnvironment =
         GraphicsEnvironment.getLocalGraphicsEnvironment(); 
         for (Font font : graphicEnvironment.getAllFonts()) {
             System.out.println(font.getFontName()); 
         }
         


        /*
         * StringBuilder builder = new StringBuilder(); FontConverterGFX app =
         * new FontConverterGFX(new Font("Meteocons", Font.PLAIN, 42));
         * app.printLetterData(builder); app = new FontConverterGFX(new
         * Font("Meteocons", Font.PLAIN, 21)); app.printLetterData(builder); app
         * = new FontConverterGFX(new Font("Meteocons", Font.PLAIN, 10));
         * app.printLetterData(builder); System.out.println(builder);
         */

         FontConverterGFX gfx = new FontConverterGFX(new Font("ArialRoundedMTBold", Font.PLAIN, 14));
        //FontConverterGFX gfx = new FontConverterGFX(new Font("Arial", Font.PLAIN, 18));

        System.out.println(gfx.getBoundingBox('a'));
        System.out.println(gfx.getBoundingBox('g'));
        System.out.println(gfx.getBoundingBox('Ä'));
        System.out.println(gfx.getBoundingBox('!'));
        System.out.println(gfx.getBoundingBox('l'));

        StringBuilder builder = new StringBuilder();
        gfx.printLetterData(builder);
        System.out.println(builder);

    }

    public String getFilename() {
        return  g.getFont().getFontName().replaceAll("[\\s\\-\\.]", "_") + "_"+ g.getFont().getSize();
    }

    public void printLetterData(StringBuilder builder) {
        List<GfxGlyph> letterList = produceLetterDataList();


        String fontName = getFilename();
        builder.append("// Created by https://oleddisplay.squix.ch/ Consider a donation\n");
        builder.append("// In case of problems make sure that you are using the font file with the correct version!\n");
        builder.append("const uint8_t " + fontName + "Bitmaps[] PROGMEM = {\n");

        builder.append("\n");
        builder.append("\t// Bitmap Data:\n");

        int counter = 0;
        int jumpPointer = 0;
        for (GfxGlyph letter : letterList) {
            letter.setBitmapOffset(jumpPointer);
            builder.append("\t");
            builder.append(letter.toHexString());
            if (counter < letterList.size() - 1) {
                builder.append(",");
            }
            builder.append(" // '" + String.valueOf(letter.getCode()) + "'\n");
            jumpPointer += letter.getBitmap().length;
            counter++;
        }

        builder.append("};\n");
        builder.append("const GFXglyph " + fontName + "Glyphs[] PROGMEM = {\n");
        builder.append("// bitmapOffset, width, height, xAdvance, xOffset, yOffset\n");
        counter = 0;
        for (GfxGlyph letter : letterList) {

            builder.append("\t");
            builder.append(String.format("  { %5d, %3d, %3d, %3d, %4d, %4d }",
                    letter.getBitmapOffset(), letter.getWidth(), letter.getHeight(),
                    letter.getXAdvance(), letter.getXOffset(), letter.getYOffset()));

            if (counter < letterList.size() - 1) {
                builder.append(",");
            }
            builder.append(" // '" + String.valueOf(letter.getCode()) + "'\n");

            counter++;
        }

        builder.append("};\n");

        builder.append("const GFXfont " + fontName + " PROGMEM = {\n");
        builder.append("(uint8_t  *)" + fontName + "Bitmaps,");
        builder.append("(GFXglyph *)" + fontName + "Glyphs,");
        builder.append(String.format("0x%02X, ", (byte) START_CHAR));
        builder.append(String.format("0x%02X, ", (byte) END_CHAR));
        builder.append(String.format("%d};", (byte) fontMetrics.getHeight()));

    }

    public List<GfxGlyph> produceLetterDataList() {
        ArrayList<GfxGlyph> letterDataList = new ArrayList<>(END_CHAR - START_CHAR);
        for (char i = START_CHAR; i < END_CHAR; i++) {
            letterDataList.add(createLetterData(i));
        }
        return letterDataList;
    }

    public GfxGlyph createLetterData(char code) {
        BufferedImage letterImage = drawLetter(code);

        Rectangle boundingBox = getBoundingBox(code);
        int height = Math.max(1, boundingBox.height);
        int width = Math.max(1, boundingBox.width + 1);


        GfxGlyph glyph = new GfxGlyph();
        glyph.setHeight(height);
        glyph.setWidth(width);
        glyph.setXOffset(boundingBox.x);
        glyph.setYOffset(boundingBox.y);
        glyph.setXAdvance(fontMetrics.charWidth(code) + 1);
        glyph.setCode(code);

        int arraySize = (int) Math.ceil(width * height / 8.0);

        int character[] = new int[arraySize];

        boolean isVisableChar = false;


        System.out.println("Char: " + String.valueOf(code) + ", " + glyph);
        /*for (int y = 0; y < height; y++) {
            System.out.println();
            for (int x = 0; x < width; x++) {
                int currentByte = character[(x + y * width)/ 8];
                int currentBit = (x + y * width) % 8;
                if (letterImage.getRGB(x, y) == Color.BLACK.getRGB()) {
                    System.out.print("X");
                    currentByte = currentByte | (1 << currentBit);
                } else {
                    currentByte = currentByte & ~(1 << currentBit);
                    System.out.print("_");
                }
                character[(x + y * width) / 8] = (byte) currentByte;
            }
        }*/
        int bitNum = 0;
        for (int y = 0; y < height; y++) {
            System.out.println();
            for (int x = 0; x < width; x++) {
                int byteNum = bitNum / 8;
                int bitPos = 7 - (bitNum % 8);
                int currentByte = character[byteNum];
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    System.out.print("X");
                    currentByte = currentByte | (1 << bitPos);
                } else {
                    System.out.print("_");
                    currentByte = currentByte & ~(1 << bitPos);
                }
                character[byteNum] = (byte) currentByte;
                bitNum++;
            }
        }
        glyph.setBitmap(character);

        return glyph;
    }

    public BufferedImage drawLetter(char code) {
        Rectangle bounds = getBoundingBox(code);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 450, 250);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(code),  - bounds.x, - bounds.y);
        return image;
    }


    private String getFontStyle() {
        Font font = g.getFont();
        if (font.isPlain()) {
            return "Plain";
        } else if (font.isItalic() && font.isBold()) {
            return "ItalicBold";
        } else if (font.isBold()) {
            return "Bold";
        } else if (font.isItalic()) {
            return "Italic";
        }
        return "";
    }

    private void writeJumpTable(StringBuilder builder, String label, int jump, int size, int width) {
        builder.append(String.format("\t0x%02X, ", (jump >> 8) & 0xFF)); // MSB
        builder.append(String.format("0x%02X, ", jump & 0xFF)); // LSB
        builder.append(String.format("0x%02X, ", size)); // byteSize
        builder.append(String.format("0x%02X, ", width)); // WIDTH
        builder.append(String.format(" // %s:%s", label, jump) + "\n");
    }

    private void writeHexValue(StringBuilder builder, String label, int value) {
        builder.append(String.format("\t0x%02X, // %s: %d", value, label, value));
        builder.append("\n");
    }

    public int getMaxCharWidth() {
        int maxWidth = 0;
        for (int i = START_CHAR; i < END_CHAR; i++) {
            maxWidth = Math.max(maxWidth, fontMetrics.charWidth((char) i));
        }
        return maxWidth;
    }

    public int getMaxCharHeight() {
        return fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() + fontMetrics.getLeading();
    }


}
