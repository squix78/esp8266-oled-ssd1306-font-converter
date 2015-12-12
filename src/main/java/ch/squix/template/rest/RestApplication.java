package ch.squix.template.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.squix.template.rest.ping.PingResource;

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

        return router;
    }

}
