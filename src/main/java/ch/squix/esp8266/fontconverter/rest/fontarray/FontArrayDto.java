package ch.squix.esp8266.fontconverter.rest.fontarray;

import lombok.Data;

@Data
public class FontArrayDto {


    private String name;
    private int size;
    private String style;
    private String libVersion;
    private String fontArray;
    private String fileName;


}
