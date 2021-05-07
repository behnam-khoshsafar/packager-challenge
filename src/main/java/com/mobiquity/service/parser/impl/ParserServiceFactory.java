package com.mobiquity.service.parser.impl;

import com.mobiquity.service.parser.ParserService;

public class ParserServiceFactory {

    private static ParserServiceImpl parserService;

    public static ParserService getInstance() {
        if (parserService == null) {
            synchronized (ParserServiceImpl.class) {
                if (parserService == null)
                    parserService = new ParserServiceImpl();
            }
        }
        return parserService;
    }

}
