package br.com.moip.rote8r.routing.service

import br.com.moip.rote8r.routing.models.Mapping
import br.com.moip.rote8r.routing.repositories.JSONRuleRepository

import java.nio.file.Files
import java.nio.file.Paths

class RoutingServiceTest extends GroovyTestCase {

    void testNoRepository() {
        def service = new RoutingService(null)
        assertNull(service.route(new Mapping()))
    }

    void testWithJSONRepository() {
        String json = loadTestJson("simple_test.json")
        def service = new RoutingService(new JSONRuleRepository(json))

        assertNull(service.route(new Mapping())) // empty input
        assertNull(service.route(new Mapping("string" : "strang"))) //input that doesn't match

        def route = service.route(new Mapping("string": "strong")) //input that matches
        assertEquals(2, route.size())
        assertEquals("value1", route.get(0).get("resultProp1"))
        assertEquals("value2", route.get(0).get("resultProp2"))
        assertEquals("value3", route.get(1).get("resultProp1"))
        assertEquals("value4", route.get(1).get("resultProp2"))
    }

    void testWithDefaultRouting() {
        String json = loadTestJson("simple_test.json")
        DefaultRouting defaultRouting = new DefaultRouting() {
            @Override
            List<Mapping> defaultRouting(Mapping paymentToRoute) {
                return Arrays.asList(new Mapping(["resultProp1" : "defaultValue1", "resultProp2" : "defaultValue2"]))
            }
        }
        def service = new RoutingService(new JSONRuleRepository(json), defaultRouting)

        def route = service.route(new Mapping()) // empty input
        assertEquals(1, route.size())
        assertEquals("defaultValue1", route.get(0).get("resultProp1"))
        assertEquals("defaultValue2", route.get(0).get("resultProp2"))

        route = service.route(new Mapping("string" : "strang")) //input that doesn't match
        assertEquals(1, route.size())
        assertEquals("defaultValue1", route.get(0).get("resultProp1"))
        assertEquals("defaultValue2", route.get(0).get("resultProp2"))

        route = service.route(new Mapping("string": "strong")) //input that matches
        assertEquals("value1", route.get(0).get("resultProp1"))
        assertEquals("value2", route.get(0).get("resultProp2"))
        assertEquals("value3", route.get(1).get("resultProp1"))
        assertEquals("value4", route.get(1).get("resultProp2"))
    }

    String loadTestJson(String path) {
        path = getClass().getClassLoader().getResource(path).getPath()
        /*
            magia preta... LOL \\A = "begin input"
            Isso faz o Scanner pegar somente um token, que é a única ocorrência do begin.
         */
        def scanner = new Scanner(Files.newInputStream(Paths.get(path))).useDelimiter("\\A")
        return scanner.next()
    }
}
