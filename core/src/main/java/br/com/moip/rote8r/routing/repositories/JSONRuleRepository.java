package br.com.moip.rote8r.routing.repositories;

import br.com.moip.rote8r.routing.models.RoutingRule;
import br.com.moip.rote8r.routing.service.RoutingRuleRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JSONRuleRepository implements RoutingRuleRepository {

    private static final Logger logger = LoggerFactory.getLogger(JSONRuleRepository.class);

    private Gson gson;
    private List<RoutingRule> rules;

    public JSONRuleRepository(String json) {
        this.gson = new GsonBuilder().create();
        this.rules = preProcess(json);
    }

    private List<RoutingRule> preProcess(String json) {
        try {
            return getRoutingRules(json);
        } catch (JsonSyntaxException jse) {
            logger.error("Couldn't parse json into list of routing rules!", jse);
            throw new IllegalArgumentException(json);
        }
    }

    private List<RoutingRule> getRoutingRules(String json) {
        RoutingRule[] rules = gson.fromJson(json, RoutingRule[].class);
        List<RoutingRule> list = new ArrayList<>(Arrays.asList(rules));
        Collections.sort(list, new Comparator<RoutingRule>() {
            @Override
            public int compare(RoutingRule o1, RoutingRule o2) {
                return o1.getPrecedence().intValue() - o2.getPrecedence().intValue();
            }
        });
        return list;
    }

    @Override
    public List<RoutingRule> findAllRulesInPrecedenceOrder() {
        return rules;
    }
}
