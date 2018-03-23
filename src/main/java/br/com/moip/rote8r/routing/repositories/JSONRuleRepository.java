package br.com.moip.rote8r.routing.repositories;

import br.com.moip.rote8r.routing.models.RoutingRule;
import br.com.moip.rote8r.routing.service.RoutingRuleRepository;

import java.util.List;

public class JSONRuleRepository implements RoutingRuleRepository {
    @Override
    public List<RoutingRule> findAllRulesInPrecedenceOrder() {
        return null; //TODO
    }
}
