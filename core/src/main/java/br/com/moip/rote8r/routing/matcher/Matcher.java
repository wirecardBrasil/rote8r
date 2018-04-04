package br.com.moip.rote8r.routing.matcher;

import br.com.moip.rote8r.routing.models.Mapping;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class Matcher {

    private static String RANGE_SEPARATOR = "..";
    private static String RANGE_SEPARATOR_REGEX = "\\.\\.";

    public static boolean matches (Mapping toTest, Mapping ruleInput) {
        for (String key : ruleInput.keySet()) {
            if (toTest.get(key) == null || !matches(toTest.get(key), ruleInput.get(key))) return false;
        }
        return true;
    }

    /****/

    private static boolean matches(Object valueToTest, Object inputValues) {
        if (inputValues instanceof List) {
            return contained(valueToTest, (List) inputValues);
        }
        return matchesSingle(valueToTest, inputValues);
    }

    private static boolean contained(Object valueToTest, List inputValues) {
        for (Object inputValue : inputValues) {
            if (matchesSingle(valueToTest, inputValue)) return true;
        }
        return false;
    }

    private static boolean matchesSingle(Object valueToTest, Object inputValue) {
        if (inputValue instanceof String) {
            return processString(valueToTest, (String)inputValue);
        }
        if (inputValue instanceof Number) {
            return processNumber(valueToTest, (Number)inputValue);
        }
        if (inputValue instanceof Boolean) {
            return processBoolean(valueToTest, (Boolean)inputValue);
        }
        return false;
    }

    private static boolean processBoolean(Object valueToTest, Boolean inputValue) {
        if (!(valueToTest instanceof Boolean)) return false;
        return valueToTest.equals(inputValue);
    }

    private static boolean processNumber(Object valueToTest, Number inputValue) {
        if (!(valueToTest instanceof Number)) return false;
        return valueToTest.equals(inputValue);
    }

    private static boolean processString(Object valueToTest, String inputValue) {
        if (isRange(inputValue)) {
            return processRange(valueToTest, inputValue);
        }
        if (!(valueToTest instanceof String)) return false;
        return valueToTest.equals(inputValue);
    }

    private static boolean isRange(String inputValue) {
        return inputValue.contains(RANGE_SEPARATOR);
    }

    private static boolean processRange(Object valueToTest, String inputValue) {
        if (!(valueToTest instanceof Number)) return false; //ranges only work for numbers
        String[] split = inputValue.split(RANGE_SEPARATOR_REGEX);
        if (split.length == 0)
            return false; //must have at least one side defined

        Number from = null;
        Number to = null;
        try {
            if (!split[0].isEmpty())
                from = NumberFormat.getInstance().parse(split[0]);
            if (split.length > 1 && !split[1].isEmpty())
                to = NumberFormat.getInstance().parse(split[1]);
        } catch (ParseException e) {
            return false; //sides of range must be numbers if present
        }
        return between((Number)valueToTest, from, to);
    }

    private static boolean between(Number valueToTest, Number from, Number to) {
        if (from != null && to != null)
            return valueToTest.doubleValue() >= from.doubleValue() && valueToTest.doubleValue() <= to.doubleValue();
        if (from != null)
            return valueToTest.doubleValue() >= from.doubleValue();
        return valueToTest.doubleValue() <= to.doubleValue();
    }
}
