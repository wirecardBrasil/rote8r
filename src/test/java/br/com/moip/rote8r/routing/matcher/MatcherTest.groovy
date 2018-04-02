package br.com.moip.rote8r.routing.matcher

import br.com.moip.rote8r.routing.models.Mapping

class MatcherTest extends GroovyTestCase {

    void testEmptyInputAndRule() {
        Mapping toTest = new Mapping()
        Mapping rule = new Mapping()

        assertTrue(Matcher.matches(toTest, rule)) //edge case
    }

    void testEmptyRule() {
        Mapping toTest = new Mapping("this": "that")
        Mapping rule = new Mapping()

        assertTrue(Matcher.matches(toTest, rule)) //anything matches empty rule
    }

    void testEmptyInput() {
        Mapping toTest = new Mapping()
        Mapping rule = new Mapping("this": "that")

        assertFalse(Matcher.matches(toTest, rule)) //input must have same keys of rule
    }

    void testWrongInputType() {
        Mapping toTest = new Mapping("this": true)
        Mapping rule = new Mapping("this": "true")

        assertFalse(Matcher.matches(toTest, rule)) //key values must have same type, if not range
    }

    void testWrongKey() {
        Mapping toTest = new Mapping("this": "those")
        Mapping rule = new Mapping("that": "those")

        assertFalse(Matcher.matches(toTest, rule)) //key values must match
    }

    void testWrongInputString() {
        Mapping toTest = new Mapping("this": "those")
        Mapping rule = new Mapping("this": "that")

        assertFalse(Matcher.matches(toTest, rule)) //key values must match
    }

    void testWrongInputNumber() {
        Mapping toTest = new Mapping("this": 41)
        Mapping rule = new Mapping("this": 42)

        assertFalse(Matcher.matches(toTest, rule)) //key values must match
    }

    void testWrongInputBoolean() {
        Mapping toTest = new Mapping("this": false)
        Mapping rule = new Mapping("this": true)

        assertFalse(Matcher.matches(toTest, rule)) //key values must match
    }

    void testExactInputString() {
        Mapping toTest = new Mapping("this": "that")
        Mapping rule = new Mapping("this": "that")

        assertTrue(Matcher.matches(toTest, rule))
    }

    void testExactInputNumber() {
        Mapping toTest = new Mapping("this": 42)
        Mapping rule = new Mapping("this": 42)

        assertTrue(Matcher.matches(toTest, rule))
    }

    void testExactInputBoolean() {
        Mapping toTest = new Mapping("this": true)
        Mapping rule = new Mapping("this": true)

        assertTrue(Matcher.matches(toTest, rule))
    }

    void testRuleList() {
        Mapping toTest = new Mapping("this": "that")
        Mapping rule = new Mapping("this": ["that", "those"])

        assertTrue(Matcher.matches(toTest, rule)) //input is one of the valid values
    }

    void testInRange() {
        Mapping toTest = new Mapping("this": 1)
        Mapping rule = new Mapping("this": "0..2")

        assertTrue(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeDouble() {
        Mapping toTest = new Mapping("this": 2.0)
        Mapping rule = new Mapping("this": "0.0..2.0")

        assertTrue(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeTopLimit() {
        Mapping toTest = new Mapping("this": 1)
        Mapping rule = new Mapping("this": "0..1")

        assertTrue(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeNoTopLimitInside() {
        Mapping toTest = new Mapping("this": 1)
        Mapping rule = new Mapping("this": "0..")

        assertTrue(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeNoTopLimitOutside() {
        Mapping toTest = new Mapping("this": 0)
        Mapping rule = new Mapping("this": "1..")

        assertFalse(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeBottomLimit() {
        Mapping toTest = new Mapping("this": 1)
        Mapping rule = new Mapping("this": "1..2")

        assertTrue(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeNoBottomLimitInside() {
        Mapping toTest = new Mapping("this": 1)
        Mapping rule = new Mapping("this": "..2")

        assertTrue(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInRangeNoBottomLimitOutside() {
        Mapping toTest = new Mapping("this": 2)
        Mapping rule = new Mapping("this": "..1")

        assertFalse(Matcher.matches(toTest, rule)) //input is inside value range
    }

    void testInMultipleRanges() {
        Mapping toTest = new Mapping("this": 4)
        Mapping rule = new Mapping("this": ["0..2", "3..5"])

        assertTrue(Matcher.matches(toTest, rule)) //input is inside one of the ranges
    }

}
