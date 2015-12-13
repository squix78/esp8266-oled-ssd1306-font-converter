package ch.squix.esp8266.fontconverter.rest;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Hello world!
 * 
 */
public class TtfBitmapFontConverter {

    private static final int END_CHAR = 256;
    private static final int START_CHAR = 32;
    private Graphics g;
    private BufferedImage image;

    public TtfBitmapFontConverter(Font font) {
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();
        g.setFont(font);
    }

    public void printFontArray(PrintStream writer) {
        String fontName = g.getFont().getFontName() + "_" + getFontStyle() + "_"
                + g.getFont().getSize();
        writer.println("const char " + fontName + "[] PROGMEM = {");
        writeHexValue(writer, "Width", getMaxCharWidth());
        writeHexValue(writer, "Height", getMaxCharHeight());
        writeHexValue(writer, "First Char", START_CHAR);
        writeHexValue(writer, "Numbers of Chars", END_CHAR - START_CHAR);
        writer.println();
        writer.println("\t// Char Widths:");
        for (int i = START_CHAR; i < END_CHAR; i++) {
            writeHexValue(writer, String.valueOf(i), g.getFontMetrics().charWidth((char) i));
        }

        // writer.println();
        // writer.println("\t// Char Start Positions:");
        //
        // // start after the end of the char widths part
        // int currentStartPos = 5 + END_CHAR-START_CHAR;
        // for (int i = START_CHAR; i < END_CHAR; i++) {
        // writeHexValue(writer, String.valueOf(i), currentStartPos);
        // int height = getMaxCharHeight();
        // int width = g.getFontMetrics().charWidth((char)i);
        // currentStartPos += width * height / 8 + 1;
        // }

        writer.println();
        writer.println("\t// Font Data:");
        for (int i = START_CHAR; i < END_CHAR; i++) {
            writeCharacterData(writer, (char) i, i == END_CHAR - 1);
        }
        writer.println("};");
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


    private void writeCharacterData(PrintStream writer, char code, boolean isLastChar) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 450, 250);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(code), 0, g.getFontMetrics().getAscent()
                + g.getFontMetrics().getLeading());
        int width = g.getFontMetrics().charWidth(code);
        int height = g.getFontMetrics().getHeight();
        int arraySize = width * height / 8 + 1;
        byte character[] = new byte[arraySize];
        int bitNum = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int byteNum = bitNum / 8;
                int bitPos = bitNum % 8;
                int currentByte = character[byteNum];
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    currentByte = currentByte | (1 << bitPos);
                } else {
                    currentByte = currentByte & ~(1 << bitPos);
                }
                character[byteNum] = (byte) currentByte;
                bitNum++;
            }
        }
        writer.print("\t");
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                writer.print(",");
            }
            writer.print(String.format("0x%02X", character[i]));
        }
        if (!isLastChar) {
            writer.print(",");
        }
        writer.println(" // " + (int) code);
    }

    private void writeHexValue(PrintStream writer, String label, int value) {
        writer.println(String.format("\t0x%02X, // %s: %d", value, label, value));
    }

    public int getCharArraySize() {
        return getMaxCharHeight() * getMaxCharWidth() / 8 + 1;
    }

    public void writeCStyleHexArrayForString(PrintStream writer, char code, int maxWidth)
            throws IOException {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 450, 250);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(code), 0, g.getFontMetrics().getAscent()
                + g.getFontMetrics().getLeading());
        int width = g.getFontMetrics().charWidth(code);
        int height = g.getFontMetrics().getHeight();
        int arraySize = getCharArraySize();

        byte character[] = new byte[arraySize];
        int bitNum = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int byteNum = bitNum / 8;
                int bitPos = bitNum % 8;
                int currentByte = character[byteNum];
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    currentByte = currentByte | (1 << bitPos);
                } else {
                    currentByte = currentByte & ~(1 << bitPos);
                }
                character[byteNum] = (byte) currentByte;
                bitNum++;
            }
        }
        writer.print("{");
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                writer.print(",");
            }
            writer.print(String.format("0x%02X", character[i]));
        }
        writer.print("}");
    }


    private void writeCStyleCharWidthArray(PrintStream printer, String text) {
        printer.print(g.getFontMetrics().stringWidth(text));
    }

    public void printPixels(String text) {

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 450, 250);
        g.setColor(Color.BLACK);
        int width = g.getFontMetrics().stringWidth(text);
        int height = g.getFontMetrics().getHeight();
        System.out.println(width + ", " + height);
        g.drawString(text, 0, g.getFontMetrics().getAscent() + g.getFontMetrics().getLeading());
        for (int x = 0; x < width; x++) {
            System.out.print("-");
        }
        System.out.println();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public int getMaxCharWidth() {
        int maxWidth = 0;
        for (int i = START_CHAR; i < END_CHAR; i++) {
            maxWidth = Math.max(maxWidth, g.getFontMetrics().charWidth((char) i));
        }
        return maxWidth;
    }

    public int getMaxCharHeight() {
        return g.getFontMetrics().getMaxAscent() + g.getFontMetrics().getMaxDescent()
                + g.getFontMetrics().getLeading();
    }

    public Graphics getGraphics() {
        return g;
    }

    public static void main(String[] args) throws InterruptedException, IOException {


        TtfBitmapFontConverter app = new TtfBitmapFontConverter(new Font("Arial", Font.PLAIN, 10));
        app.printFontArray(System.out);
        app = new TtfBitmapFontConverter(new Font("Arial", Font.PLAIN, 16));
        app.printFontArray(System.out);
        app = new TtfBitmapFontConverter(new Font("Arial", Font.PLAIN, 24));
        app.printFontArray(System.out);

    }

}
