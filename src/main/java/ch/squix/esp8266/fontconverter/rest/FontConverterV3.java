package ch.squix.esp8266.fontconverter.rest;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FontConverterV3 {

    private static final int END_CHAR = 256;
    private static final int START_CHAR = 32;

    private Graphics2D g;
    private FontMetrics fontMetrics;
    private BufferedImage image;


    public FontConverterV3(Font font) {
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setFont(font);
        fontMetrics = g.getFontMetrics();
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        /*
         * GraphicsEnvironment graphicEnvironment =
         * GraphicsEnvironment.getLocalGraphicsEnvironment(); for (Font font :
         * graphicEnvironment.getAllFonts()) {
         * System.out.println(font.getFontName()); }
         */

        StringBuilder builder = new StringBuilder();

        FontConverterV3 app = new FontConverterV3(new Font("Meteocons", Font.PLAIN, 42));
        app.printLetterData(builder);
        app = new FontConverterV3(new Font("Meteocons", Font.PLAIN, 21));
        app.printLetterData(builder);
        app = new FontConverterV3(new Font("Meteocons", Font.PLAIN, 10));
        app.printLetterData(builder);

        System.out.println(builder);
    }


    public void printLetterData(StringBuilder builder) {
        List<LetterData> letterList = produceLetterDataList();

        String fontName = g.getFont().getFontName().replaceAll("[\\s\\-\\.]", "_") + "_"
                + getFontStyle() + "_" + g.getFont().getSize();
        builder.append("// Created by http://oleddisplay.squix.ch/ Consider a donation\n");
        builder.append("// In case of problems make sure that you are using the font file with the correct version!\n");
        builder.append("const char " + fontName + "[] PROGMEM = {\n");
        writeHexValue(builder, "Width", getMaxCharWidth());
        writeHexValue(builder, "Height", getMaxCharHeight());
        writeHexValue(builder, "First Char", START_CHAR);
        writeHexValue(builder, "Numbers of Chars", END_CHAR - START_CHAR);
        builder.append("\n");
        builder.append("\t// Jump Table:\n");

        int lastJumpPoint = 0;
        for (LetterData letter : letterList) {
            int letterWidth = letter.getWidth();
            int size = letter.getByteSize();
            String code = "" + ((int) letter.getCode());
            if (letter.isVisable()) {
                writeJumpTable(builder, code, lastJumpPoint, size, letterWidth);
                lastJumpPoint += size;
            } else {
                writeJumpTable(builder, code, 0xFFFF, size, letterWidth);
            }
        }

        builder.append("\n");
        builder.append("\t// Font Data:\n");

        for (LetterData letter : letterList) {
            if (letter.isVisable()) {
                builder.append("\t");
                builder.append(letter.toString());
                if ((int) letter.getCode() != END_CHAR - 1) {
                    builder.append(",");
                }
                builder.append("\t// " + (int) letter.getCode() + "\n");
            }
        }

        builder.append("};\n");
    }

    public List<LetterData> produceLetterDataList() {
        ArrayList<LetterData> letterDataList = new ArrayList<>(END_CHAR - START_CHAR);
        for (char i = START_CHAR; i < END_CHAR; i++) {
            letterDataList.add(createLetterData(i));
        }
        return letterDataList;
    }

    public LetterData createLetterData(char code) {
        BufferedImage letterImage = drawLetter(code);

        int width = fontMetrics.charWidth(code);
        int height = fontMetrics.getHeight();

        int arrayHeight = (int) Math.ceil((double) height / 8.0);
        int arraySize = width * arrayHeight;

        int character[] = new int[arraySize];

        boolean isVisableChar = false;

        if (width > 0) {
            for (int i = 0; i < arraySize; i++) {
                int xImg = (i / arrayHeight);
                int yImg = (i % arrayHeight) * 8;
                int currentByte = 0;
                for (int b = 0; b < 8; b++) {
                    if (yImg + b <= height) {
                        if (letterImage.getRGB(xImg, yImg + b) == Color.BLACK.getRGB()) {
                            isVisableChar = true;
                            currentByte = currentByte | (1 << b);
                        } else {
                            currentByte = currentByte & ~(1 << b);
                        }
                    }
                }

                character[i] = (byte) currentByte;
            }
        }

        // Remove rightmost zeros to save bytes
        int lastByteNotNull = -1;
        for (int i = 0; i < character.length; i++) {
            if (character[i] != 0)
                lastByteNotNull = i;
        }

        character = Arrays.copyOf(character, lastByteNotNull + 1);

        return new LetterData(code, character, width, height, isVisableChar);
    }

    public BufferedImage drawLetter(char code) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 450, 250);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(code), 0, fontMetrics.getAscent() + fontMetrics.getLeading());
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

    public class LetterData {

        private char code;

        private int[] bytes;
        private int width;
        private int height;

        private boolean visable;

        public LetterData(char code, int[] bytes, int width, int height, boolean visable) {
            this.code = code;
            this.bytes = bytes;
            this.width = width;
            this.height = height;
            this.visable = visable;
        }

        public char getCode() {
            return code;
        }

        public int[] getBytes() {
            return bytes;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public boolean isVisable() {
            return visable;
        }

        public int getByteSize() {
            return bytes.length;
        }

        public String toString() {
            if (bytes.length <= 0 || !visable) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(String.format("0x%02X", (byte) bytes[i]));
            }
            return builder.toString();

        }
    }

}
