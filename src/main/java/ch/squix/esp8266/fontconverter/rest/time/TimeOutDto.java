package ch.squix.esp8266.fontconverter.rest.time;

import lombok.Data;

@Data
public class TimeOutDto {

    private String language;
    private String country;
    private String timeZoneId;
    private String dateFormat;
    private String timeFormat;

    private Integer secondsOfHour;
    private Integer minutesOfHour;
    private Integer hoursOfDay;
    private Integer dayOfMonth;
    private Integer dayOfWeek;
    private Integer weekOfYear;
    private Integer monthOfYear;
    private Integer year;

    private String monthAsShortText;
    private String monthAsLongText;
    private String dayAsShortText;
    private String dayAsLongText;

    private String timeFormatted;
    private String dateFormatted;


}
