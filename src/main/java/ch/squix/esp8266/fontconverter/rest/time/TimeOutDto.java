package ch.squix.esp8266.fontconverter.rest.time;

import java.util.List;

import lombok.Data;

@Data
public class TimeOutDto {

    private long millisOfDayUtc;
    private List<TimeZoneDto> timeZoneDto;


}
