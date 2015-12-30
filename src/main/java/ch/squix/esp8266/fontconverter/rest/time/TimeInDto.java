package ch.squix.esp8266.fontconverter.rest.time;

import lombok.Data;

@Data
public class TimeInDto {

    private String language;
    private String country;
    private String timeZoneId;
    private String dateFormat;
    private String timeFormat;

}
