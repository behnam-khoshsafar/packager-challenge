package com.mobiquity.service.formatter.impl;

import com.mobiquity.service.formatter.FormatterService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatterServiceFactoryTest {

    @Test
    void onlyOnceInstanceCreatedWhenGetInstance() {

        FormatterService formatterServiceFirstInstance = FormatterServiceFactory.getInstance();
        FormatterService formatterServiceSecondInstance = FormatterServiceFactory.getInstance();

        assertEquals(formatterServiceFirstInstance, formatterServiceSecondInstance);
    }

}