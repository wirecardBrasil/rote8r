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

    public RoutingService(RoutingRuleRepository routingRuleRepository, DefaultRouting defaultRouting) {
        this.routingRuleRepository = routingRuleRepository;
        this.defaultRouting = defaultRouting;
    }

    public List<Mapping> defineAcquirersForPayment(Mapping paymentToRoute) {
        logger.info("Trying to determine acquirer route for payment " + paymentToRoute);
        try {
            List<RoutingRule> allRules = routingRuleRepository.findAllRulesInPrecedenceOrder();

            for (RoutingRule rule : allRules) {
                logger.debug("Checking if rule matches: " + rule);
                if (rule.matches(paymentToRoute)) {
                    List<Mapping> result = rule.getPlatforms();
                    if (result != null) {
                        logger.info("Found matching rule: " + rule);
//                        persistRoutingResult(result, paymentToRoute);
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("We have failed miserably... Error: " + e.getMessage(), e);
        }

        logger.info("Could not find any matching rules, using default routing!");
        List<Mapping> result = defaultRouting.defaultRouting(paymentToRoute);
//        persistRoutingResult(result, paymentToRoute);
        return result;
    }
}
