package ch.squix.esp8266.fontconverter.rest.ping;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class PingResource extends ServerResource {

    @Get(value = "json")
    public PingDto execute() throws UnsupportedEncodingException {
        PingDto dto = new PingDto();
        dto.setAppName("ESP8266");
        dto.setVersionId("1.0.0");
        return dto;
    }

}
