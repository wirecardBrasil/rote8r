package br.com.moip.rote8r.routing.repositories;

import br.com.moip.rote8r.routing.models.RoutingRule;
import br.com.moip.rote8r.routing.service.RoutingRuleRepository;
import com.mongodb.MongoClientURI;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.BasicQuery;

import java.util.List;

public class MongoDBRuleRepository implements RoutingRuleRepository {

    private MongoOperations mongoOperations;
    private String routingRuleCollectionName;

    public MongoDBRuleRepository(String mongoUri, String routingRuleCollectionName) {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoUri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongoClientURI);
        this.mongoOperations = new MongoTemplate(factory);
        this.routingRuleCollectionName = routingRuleCollectionName;
    }

    public MongoDBRuleRepository(MongoOperations mongoOperations, String routingRuleCollectionName) {
        this.mongoOperations = mongoOperations;
        this.routingRuleCollectionName = routingRuleCollectionName;
    }

    public List<RoutingRule> findAllRulesInPrecedenceOrder() {
        BasicQuery query = (BasicQuery) new BasicQuery("{}").with(new Sort(Sort.Direction.ASC, "precedence"));
        return mongoOperations.find(query, RoutingRule.class, routingRuleCollectionName);
    }
}
