package ch.squix.esp8266.fontconverter.rest;

import lombok.Data;

@Data
public class GfxGlyph {

    private int bitmapOffset;
    private int width;
    private int height;
    private int xAdvance;
    private int xOffset;
    private int yOffset;
    private int[] bitmap;
    private char code;

    public String toHexString() {
        if (bitmap.length <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bitmap.length; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(String.format("0x%02X", (byte) bitmap[i]));
        }
        return builder.toString();

    }

}
