package com.mobiquity.service.parser.impl;

import com.mobiquity.service.parser.ParserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserServiceFactoryTest {

    @Test
    void onlyOnceInstanceCreatedWhenGetInstance(){

        ParserService parserServiceFirstInstance =ParserServiceFactory.getInstance();
        ParserService parserServiceSecondInstance =ParserServiceFactory.getInstance();

        assertEquals(parserServiceFirstInstance,parserServiceSecondInstance);
    }

}