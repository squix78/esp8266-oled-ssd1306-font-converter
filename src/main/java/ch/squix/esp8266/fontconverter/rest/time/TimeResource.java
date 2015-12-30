package ch.squix.esp8266.fontconverter.rest.time;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.restlet.engine.util.StringUtils;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class TimeResource extends ServerResource {

    @Post(value = "json")
    public TimeOutDto execute(TimeInDto inDto) throws FontFormatException, IOException {
        DateTimeZone zone = DateTimeZone.forID(inDto.getTimeZoneId());
        Locale locale = new Locale(inDto.getLanguage(), inDto.getCountry());
        DateTime dateTime = new DateTime(zone);

        TimeOutDto outDto = new TimeOutDto();
        outDto.setLanguage(inDto.getLanguage());
        outDto.setCountry(inDto.getCountry());
        outDto.setTimeZoneId(inDto.getTimeZoneId());
        outDto.setTimeFormat(inDto.getTimeFormat());
        outDto.setDateFormatted(inDto.getTimeFormat());

        outDto.setSecondsOfHour(dateTime.getSecondOfMinute());
        outDto.setSecondsOfDay(dateTime.getSecondOfDay());
        outDto.setMinutesOfHour(dateTime.getMinuteOfHour());
        outDto.setHoursOfDay(dateTime.getHourOfDay());
        outDto.setDayOfWeek(dateTime.getDayOfWeek());
        outDto.setDayOfMonth(dateTime.getDayOfMonth());
        outDto.setWeekOfYear(dateTime.getWeekOfWeekyear());
        outDto.setMonthOfYear(dateTime.getMonthOfYear());
        outDto.setYear(dateTime.getYear());

        outDto.setDayAsShortText(dateTime.dayOfWeek().getAsShortText(locale));
        outDto.setDayAsLongText(dateTime.dayOfWeek().getAsText(locale));
        outDto.setMonthAsShortText(dateTime.monthOfYear().getAsShortText(locale));
        outDto.setMonthAsLongText(dateTime.monthOfYear().getAsText(locale));

        String timeFormat = inDto.getTimeFormat();
        if (!StringUtils.isNullOrEmpty(timeFormat)) {
            outDto.setTimeFormatted(dateTime.toString(timeFormat, locale));
        }
        String dateFormat = inDto.getDateFormat();
        if (!StringUtils.isNullOrEmpty(dateFormat)) {
            outDto.setDateFormatted(dateTime.toString(dateFormat, locale));
        }
        return outDto;
    }

}
