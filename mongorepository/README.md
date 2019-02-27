# Rote8r MongoDB Repository

Uses Spring Data MongoDB to read routing rules from MongoDB database.

## Usage

Create a new MongoDBRuleRepository passing the mongo uri and routing rule collection name as arguments to the constructor:

```
RoutingRuleRepository repo = new MongoDBRuleRepository(
  "mongodb://[username:password@]host1[:port1][,...hostN[:portN]]][/[database][?options]]",
  "my_routing_rules");
```

This will look in the `my_routing_rules` collection and retrieve all documents from the collection as rules.
