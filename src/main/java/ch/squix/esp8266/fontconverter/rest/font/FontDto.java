package ch.squix.esp8266.fontconverter.rest.font;

import lombok.Data;

@Data
public class FontDto {

    private String name;
    private String fontFamily;
    private boolean isPlain;
    private boolean isBold;
    private boolean isItalic;
}
