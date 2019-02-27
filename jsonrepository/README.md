# Rote8r Json Repository

Uses Gson to read routing rules from JSON files.

## Usage

Create a new JSONRuleRepository passing the JSON as a String to the constructor:

```
RoutingRuleRepository repo = new JSONRuleRepository("[
{
  \"precedence\": 1,
  \"input\": {
    \"key\": \"value\"
  },
  \"output\": {
    \"platforms\": [
      \"key\": \"value\"
    ]
  }
}
]")
```

The JSON should be an array with all routing rules to be used. 
The contents could be retrieved from a resource file, for example.
