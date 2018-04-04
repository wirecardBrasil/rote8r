package br.com.moip.rote8r.routing.models;

import java.util.List;

/**
 * Created by fernando on 30/11/15.
 */
public class RoutingOutput {

    private final List<Mapping> platforms;

    public RoutingOutput(List<Mapping> platforms) {
        this.platforms = platforms;
    }

    public List<Mapping> getPlatforms() {
        return platforms;
    }

    @Override
    public String toString() {
        return "RoutingOutput{" +
                "platforms=" + platforms +
                '}';
    }
}
