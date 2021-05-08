package com.mobiquity.service.formatter.impl;

import com.mobiquity.service.formatter.FormatterService;

/**
 * Factory class for creation of formatter service.
 */
public class FormatterServiceFactory {

    private static FormatterService formatterService;

    public static FormatterService getInstance() {
        if (formatterService == null) {
            synchronized (FormatterServiceImpl.class) {
                if (formatterService == null)
                    formatterService = new FormatterServiceImpl();
            }
        }
        return formatterService;
    }

}
