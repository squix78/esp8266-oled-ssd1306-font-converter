package ch.squix.esp8266.fontconverter.rest.font.preview;

import lombok.Getter;

import java.awt.*;

@Getter
public enum PreviewDisplay {

    OLED96("OLED96.png", 2, new Rectangle(8, 60, 2*128, 2*64)),
    TFT24("TFT24.png", 1, new Rectangle(15, 47, 240, 320));

    private String filename;
    private Integer zoomFactor;
    private Rectangle clipRect;

    PreviewDisplay(String filename, Integer zoomFactor, Rectangle clipRect) {
        this.filename = filename;
        this.zoomFactor = zoomFactor;
        this.clipRect = clipRect;
    }

}
