package br.com.moip.rote8r.routing.service

import br.com.moip.rote8r.routing.models.Mapping
import br.com.moip.rote8r.routing.models.RoutingOutput
import br.com.moip.rote8r.routing.models.RoutingRule

class RoutingServiceTest extends GroovyTestCase {

    void testNoRepository() {
        def service = new RoutingService(null)
        assertNull(service.route(new Mapping()))
    }

    void testWithJSONRepository() {
        def service = new RoutingService(new TestRepository())

        assertNull(service.route(new Mapping())) // empty input
        assertNull(service.route(new Mapping("string" : "strang"))) //input that doesn't match

        def route = service.route(new Mapping("string": "strong")).platforms //input that matches
        assertEquals(2, route.size())
        assertEquals("value1", route.get(0).get("resultProp1"))
        assertEquals("value2", route.get(0).get("resultProp2"))
        assertEquals("value3", route.get(1).get("resultProp1"))
        assertEquals("value4", route.get(1).get("resultProp2"))
    }

    void testWithDefaultRouting() {
        DefaultRouting defaultRouting = new DefaultRouting() {
            @Override
            RoutingRule defaultRouting(Mapping paymentToRoute) {
                return new RoutingRule(
                        new Mapping(),
                        new RoutingOutput(Arrays.asList(new Mapping(["resultProp1": "defaultValue1", "resultProp2": "defaultValue2"]))),
                        1
                )
            }
        }
        def service = new RoutingService(new TestRepository(), defaultRouting)

        def route = service.route(new Mapping()).platforms // empty input
        assertEquals(1, route.size())
        assertEquals("defaultValue1", route.get(0).get("resultProp1"))
        assertEquals("defaultValue2", route.get(0).get("resultProp2"))

        route = service.route(new Mapping("string" : "strang")).platforms //input that doesn't match
        assertEquals(1, route.size())
        assertEquals("defaultValue1", route.get(0).get("resultProp1"))
        assertEquals("defaultValue2", route.get(0).get("resultProp2"))

        route = service.route(new Mapping("string": "strong")).platforms //input that matches
        assertEquals("value1", route.get(0).get("resultProp1"))
        assertEquals("value2", route.get(0).get("resultProp2"))
        assertEquals("value3", route.get(1).get("resultProp1"))
        assertEquals("value4", route.get(1).get("resultProp2"))
    }

}

class TestRepository implements RoutingRuleRepository {

    @Override
    List<RoutingRule> findAllRulesInPrecedenceOrder() {
        return new ArrayList<>(Arrays.asList(new RoutingRule(
                name: "TESTE",
                precedence: 1,
                input: new Mapping(
                        "string" : "strong"
                ),
                output: new RoutingOutput(
                        new ArrayList<Mapping>(Arrays.asList(new Mapping(
                                "resultProp1" : "value1",
                                "resultProp2" : "value2"
                        ), new Mapping(
                                "resultProp1" : "value3",
                                "resultProp2" : "value4"
                        )))
                )
        )))
    }
}