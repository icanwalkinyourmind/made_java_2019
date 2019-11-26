package ru.made.ex.three;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

class Person {
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String[] phoneNumbers;

    Person(String firstName, String lastName, Address address, String[] phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }
}

class Address {
    private final String city;
    private final String postalCode;

    Address(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }
}


public class SimpleSerializerTest {
    private static final Person testObject = new Person("Vlad", "Medvedev",
            new Address("Moscow", "66666"), new String[]{"12323", "123213213", "89123231"});

    private static final String xmlAnswer =
                    "<Person>\n" +
                    "  <firstName>Vlad</firstName>\n" +
                    "  <lastName>Medvedev</lastName>\n" +
                    "  <address>\n" +
                    "    <city>Moscow</city>\n" +
                    "    <postalCode>66666</postalCode>\n" +
                    "  </address>\n" +
                    "  <phoneNumbers>\n" +
                    "    <1>12323</1>\n" +
                    "    <2>123213213</2>\n" +
                    "    <3>89123231</3>\n" +
                    "  </phoneNumbers>\n" +
                    "</Person>\n";
    private static final String jsonAnswer =
                    "{\n" +
                    "  \"firstName\": \"Vlad\",\n" +
                    "  \"lastName\": \"Medvedev\",\n" +
                    "  \"address\": {\n" +
                    "    \"city\": \"Moscow\",\n" +
                    "    \"postalCode\": \"66666\"\n" +
                    "  },\n" +
                    "  \"phoneNumbers\": [\n" +
                    "    \"12323\",\n" +
                    "    \"123213213\",\n" +
                    "    \"89123231\"\n" +
                    "  ]\n" +
                    "}\n";

    @Test
    public void serializeXml() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Serializer s = new SimpleSerializer(new XmlSerializationStrategy());
        assertEquals(SimpleSerializerTest.xmlAnswer, s.serialize(SimpleSerializerTest.testObject));
    }

    @Test
    public void serializeJson() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Serializer s = new SimpleSerializer(new JsonSerializationStrategy());
        assertEquals(SimpleSerializerTest.jsonAnswer, s.serialize(SimpleSerializerTest.testObject));
    }
}
