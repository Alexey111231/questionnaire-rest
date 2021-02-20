package ru.vk.sladkiipirojok.questionnairerest.controller;

public class TestData {
    public static final String POST_USER = "{\n" +
            "\"name\":\"Questins\",\n" +
            "\"beginDate\":\"2005-08-09\",\n" +
            "\"endDate\":\"2007-08-09\",\n" +
            "\"active\":\"true\",\n" +
            "\"questions\":[\n" +
            "{\n" +
            "\"text\": \"req\",\n" +
            "\"order\": \"1\"\n" +
            "},\n" +
            "{\n" +
            "\"text\": \"faq\",\n" +
            "\"order\": \"2\"\n" +
            "}\n" +
            "]\n" +
            "}";

    public static final String UNSORTED_ERROR_MESSAGE = "Unsorted";

    public static final String UNCORRECTED_FIELD_ERROR_MESSAGE = "Uncorrected sort field";
}
