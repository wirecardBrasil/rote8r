package br.com.moip.rote8r.routing.models;

import br.com.moip.rote8r.routing.matcher.Matcher;

import java.util.List;

/**
 * Created by fernando on 30/11/15.
 */
public class RoutingRule {

    private String name;

    private Long precedence;

    private Mapping input;

    private RoutingOutput output;

    public RoutingRule() {}

    public RoutingRule(Mapping input, RoutingOutput output, Long precedence) {
        this.input = input;
        this.output = output;
        this.precedence = precedence;
    }

    public boolean matches(Mapping paymentToRoute) {
        if (input == null) return true; //regra sem input bate com tudo por definição
        return Matcher.matches(paymentToRoute, input);
    }

    public List<Mapping> getPlatforms() {
        if (output == null) return null;
        return output.getPlatforms();
    }

    public String getName() {
        return name;
    }

    public Mapping getInput() {
        return input;
    }

    public RoutingOutput getOutput() {
        return output;
    }

    public Long getPrecedence() {
        return precedence;
    }

    @Override
    public String toString() {
        return "RoutingRule{" +
                "name='" + name + '\'' +
                ", precedence=" + precedence +
                ", input=" + input +
                ", output=" + output +
                '}';
    }
}
