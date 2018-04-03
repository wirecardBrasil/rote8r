package br.com.moip.rote8r.routing.service;

import br.com.moip.rote8r.routing.models.Mapping;
import br.com.moip.rote8r.routing.models.RoutingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by fernando on 30/11/15.
 */
public class RoutingService {

    private static final Logger logger = LoggerFactory.getLogger(RoutingService.class);

    private RoutingRuleRepository routingRuleRepository;
    private DefaultRouting defaultRouting;

    public RoutingService(RoutingRuleRepository routingRuleRepository) {
        this.routingRuleRepository = routingRuleRepository;
    }

    public RoutingService(RoutingRuleRepository routingRuleRepository, DefaultRouting defaultRouting) {
        this.routingRuleRepository = routingRuleRepository;
        this.defaultRouting = defaultRouting;
    }

    public List<Mapping> route(Mapping toRoute) {
        logger.info("Trying to determine route for mapping: " + toRoute);
        if (routingRuleRepository == null) {
            logger.warn("No routingRuleRepository supplied to rote8r!");
            return null;
        }
        List<Mapping> result = findMatchingRule(toRoute);
        if (result != null) return result;

        logger.info("Could not find any matching rules, using default routing!");
        if (defaultRouting == null) {
            logger.warn("No default routing supplied to rote8r!");
            return null;
        }
        return defaultRouting.defaultRouting(toRoute);
    }

    private List<Mapping> findMatchingRule(Mapping toRoute) {
        try {
            List<RoutingRule> allRules = routingRuleRepository.findAllRulesInPrecedenceOrder();

            for (RoutingRule rule : allRules) {
                logger.debug("Checking if rule matches: " + rule);
                if (rule.matches(toRoute)) {
                    List<Mapping> result = rule.getPlatforms();
                    if (result != null) {
                        logger.info("Found matching rule: " + rule);
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("We have failed miserably... Error: " + e.getMessage(), e);
        }
        return null;
    }
}
