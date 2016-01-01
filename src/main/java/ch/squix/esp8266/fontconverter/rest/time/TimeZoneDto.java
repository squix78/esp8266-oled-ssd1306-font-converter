package ch.squix.esp8266.fontconverter.rest.time;

import lombok.Data;

@Data
public class TimeZoneDto {

    private Integer index;
    private String timeZoneId;
    private long timeZoneOffsetToUtcMillis;
    private String formattedDate;

}
