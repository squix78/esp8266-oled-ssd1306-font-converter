package ch.squix.esp8266.fontconverter.rest.ping;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingResource {

    @GetMapping("/rest/ping")
    public PingDto execute() throws UnsupportedEncodingException {
        PingDto dto = new PingDto();
        dto.setAppName("ESP8266");
        dto.setVersionId("1.0.0");
        return dto;
    }

}
