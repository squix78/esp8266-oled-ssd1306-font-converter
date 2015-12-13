package ch.squix.esp8266.fontconverter.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.squix.esp8266.fontconverter.rest.font.FontFamilyResource;
import ch.squix.esp8266.fontconverter.rest.ping.PingResource;

public class RestApplication extends Application {

    static {
        // ObjectifyService.register(PairRate.class);
        // ObjectifyService.register(SpeakerRate.class);
        // ObjectifyService.register(Highscore.class);
    }

    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());
        router.attach("/ping", PingResource.class);
        router.attach("/font", FontFamilyResource.class);

        return router;
    }

}
