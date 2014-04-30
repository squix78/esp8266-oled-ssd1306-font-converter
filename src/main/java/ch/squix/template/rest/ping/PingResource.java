package ch.squix.template.rest.ping;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;


public class PingResource extends ServerResource {

    @Get(value = "json")
    public PingDto execute() throws UnsupportedEncodingException {
        Environment env = ApiProxy.getCurrentEnvironment();
        PingDto dto = new PingDto();
        dto.setAppName(env.getAppId());
        dto.setVersionId(env.getVersionId());
        return dto;
    }

}
