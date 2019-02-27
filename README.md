# rote8r
The man to roteate them all

## What is rote8r for?

Suppose you have a service which can be provided by N providers, all of which obey the same interface but have different parameters 
(as in cost, performance, efficiency, etc.). If you have all providers implemented and ready to use, you must have some kind of strategy
to choose which provider to use.

In some cases this decision is easy, like when there is a favorite provider which supports all your use cases, or is the cheapest, etc.
However as your business gets more complex there is a good chance that you may have to be able to change your provider based on 
the current use case, for example, using a certain acquirer to authorize a specific credit card payment.

Rote8r is a library that provides a flexible routing mechanism, enabling you to define and change rules which determine what provider
should be used in each situation. Its "schemaless" approach allows you to have anything as routing input and output.

## How it works

Rote8r retrieves its rules from a Repository. You can use an existing repository implementation or create your own.

The repository provides rote8r with a List of RoutingRules, which have the following attributes:
- name: a label to help you organize the rules, it has no effect otherwise.
- input: the data which will be matched against each rule. If absent, the rule will always match.
- output (required): the data that defines which provider(s) must be used if the rule matches.
- precedence (required): an integer that defines the priority of this rule. Lower numbers mean the rule has more priority. 

This list is sorted by ascending precedence, and then it attempts to match your use case against each rule, stopping after 
the first match is found. You define what data is used for matching, so you can for example use a payment's amount as input,
and define that a certain rule matches if the payment's amount is higher than a value:

``` 
{
    "name": "My cool rule #1",
    "precedence": 1,
    "input": {
        "amount": "1000.."
    },
    "output": {
        "platforms": [
            {
                "acquirer": "ACQUIRER1"
            }
        ]
    }
}
```

You also define what is the output, so in the example above the "ACQUIRER1" acquirer is the result of the routing. 
You can also return more than one result; this is useful when you wish to implement retrying in case a provider fails.
In this case you have to prepare your application to correctly handle the possibility of multiple results.

## Matching capabilities

You can match values by:
- Exact value (e.g. `amount: 1000`)
- Number range (lower / upper / both) (e.g. `amount: "1000.."` or `amount: "..2000"` or `amount: "1000..2000"`)
- List (contains value) (e.g. `amount: [1000, 2000])

Values can be booleans `true / false`, numbers `1000 / 20.0` or Strings `"OPTION1" / "OPTION2"`

## Repositories

Rote8r comes with a JsonRepository to read rules from JSON files, and a MongoRepository that uses Spring Data to read rules from MongoDB. 
To use them just add the module along with the core module. Refer to the documentation of each module for usage instructions.
