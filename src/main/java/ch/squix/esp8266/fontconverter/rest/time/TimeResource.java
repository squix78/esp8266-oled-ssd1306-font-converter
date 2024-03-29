package ch.squix.esp8266.fontconverter.rest.time;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TimeResource {

    @PostMapping("/rest/time")
    public TimeOutDto execute(@RequestBody TimeInDto inDto) throws FontFormatException, IOException {
        log.info("Load time for time zones: {}", inDto);
        TimeOutDto outDto = new TimeOutDto();
        DateTime dateTime = new DateTime(DateTimeZone.UTC);

        Locale locale = new Locale(inDto.getLanguage(), inDto.getCountry());
        outDto.setMillisOfDayUtc(dateTime.getMillisOfDay());

        List<TimeZoneDto> timeZones = new ArrayList<>();
        outDto.setTimeZoneDto(timeZones);

        for (String timeZoneId : inDto.getTimeZoneIds()) {
            DateTimeZone zone = DateTimeZone.forID(timeZoneId);
            DateTime localDateTime = new DateTime(zone);
            TimeZoneDto zoneDto = new TimeZoneDto();
            zoneDto.setIndex(timeZones.size());
            zoneDto.setTimeZoneId(timeZoneId);
            zoneDto.setTimeZoneOffsetToUtcMillis(zone.getOffset(dateTime.getMillis()));
            zoneDto.setFormattedDate(localDateTime.toString(inDto.getDateFormat(), locale));
            timeZones.add(zoneDto);

        }
        return outDto;
    }

}
