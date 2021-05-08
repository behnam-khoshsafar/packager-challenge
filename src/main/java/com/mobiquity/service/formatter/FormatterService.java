package com.mobiquity.service.formatter;

import java.util.List;

public interface FormatterService {

    /**
     * format the list of items.
     *
     * @return String formatted of input based on concrete class that used.
     */
    String formatItems(List<Integer> numbers);

    /**
     * format the list of packs item.
     *
     * @return String formatted of input based on concrete class that used.
     */
    String formatPacks(List<String> list);

}
