package br.com.moip.rote8r.routing.repositories

class JSONRuleRepositoryTest extends GroovyTestCase {

    void testEmptyJson() {
        def repository = new JSONRuleRepository("[]")
        def rules = repository.findAllRulesInPrecedenceOrder()

        assertEquals(0, rules.size())
    }

    void testSimpleJson() {
        def json = """\
        [
            {
                "name": "TESTE",
                "precedence": 1,
                "input": {
                    "string" : "strong",
                    "int" : 1,
                    "boolean" : true,
                    "range" : "0..10",
                    "list" : ["some", "list", "elements"]
                },
                "output": {
                    "platforms": [
                        {
                            "resultProp1" : "value1",
                            "resultProp2" : "value2"
                        },
                        {
                            "resultProp1" : "value3",
                            "resultProp2" : "value4"
                        }
                    ]
                }
            }
        ]
        """
        def repository = new JSONRuleRepository(json)
        def rules = repository.findAllRulesInPrecedenceOrder()
        assertEquals(1, rules.size())

        def rule = rules.get(0)
        assertEquals("TESTE", rule.name)
        assertEquals(1, rule.precedence)

        def input = rule.input
        assertEquals("strong", input.get("string"))
        assertEquals(1, input.get("int"))
        assertEquals(true, input.get("boolean"))
        assertEquals("0..10", input.get("range"))

        List list = input.get("list") as List
        assertEquals("some", list.get(0))
        assertEquals("list", list.get(1))
        assertEquals("elements", list.get(2))

        def output1 = rule.output.platforms.get(0)
        assertEquals("value1", output1.get("resultProp1"))
        assertEquals("value2", output1.get("resultProp2"))

        def output2 = rule.output.platforms.get(1)
        assertEquals("value3", output2.get("resultProp1"))
        assertEquals("value4", output2.get("resultProp2"))
    }

    void testOrder() {
        def json = """\
        [
            {
                "name": "SECOND",
                "precedence": 2
            },
            {
                "name": "THIRD",
                "precedence": 3
            },
            {
                "name": "FIRST",
                "precedence": 1
            }
        ]
        """
        def repository = new JSONRuleRepository(json)
        def rules = repository.findAllRulesInPrecedenceOrder()
        assertEquals(3, rules.size())

        assertEquals("FIRST", rules.get(0).name)
        assertEquals(1, rules.get(0).precedence)

        assertEquals("SECOND", rules.get(1).name)
        assertEquals(2, rules.get(1).precedence)

        assertEquals("THIRD", rules.get(2).name)
        assertEquals(3, rules.get(2).precedence)
    }

}
