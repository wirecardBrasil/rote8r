package br.com.moip.rote8r.routing.service;

import br.com.moip.rote8r.routing.models.Mapping;

import java.util.List;

/**
 * Created by fernando on 01/12/15.
 */
public interface DefaultRouting {

    List<Mapping> defaultRouting(Mapping paymentToRoute);

}
