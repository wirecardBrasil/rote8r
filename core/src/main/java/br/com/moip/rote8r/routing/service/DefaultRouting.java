package br.com.moip.rote8r.routing.service;

import br.com.moip.rote8r.routing.models.Mapping;
import br.com.moip.rote8r.routing.models.RoutingRule;

import java.util.List;

/**
 * Created by fernando on 01/12/15.
 */
public interface DefaultRouting {

    RoutingRule defaultRouting(Mapping paymentToRoute);

}
