package br.com.moip.rote8r.routing.service;

import br.com.moip.rote8r.routing.models.RoutingRule;

import java.util.List;

/**
 * Created by fernando on 30/11/15.
 */
public interface RoutingRuleRepository {

    public List<RoutingRule> findAllRulesInPrecedenceOrder();

}
