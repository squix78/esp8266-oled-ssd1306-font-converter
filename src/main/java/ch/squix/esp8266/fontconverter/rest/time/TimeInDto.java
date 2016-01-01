package ch.squix.esp8266.fontconverter.rest.time;

import java.util.List;

import lombok.Data;

@Data
public class TimeInDto {

    private String language;
    private String country;
    private List<String> timeZoneIds;
    private String dateFormat;

}
