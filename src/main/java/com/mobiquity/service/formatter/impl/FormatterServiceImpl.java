package com.mobiquity.service.formatter.impl;

import com.mobiquity.service.formatter.FormatterService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete class to format items and packs.
 */
public class FormatterServiceImpl implements FormatterService {

    /**
     * format the list of items by joining them with a comma separator.
     *
     * @return String formatted of input by joining them with a comma separator.
     */
    @Override
    public String formatItems(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return "-";
        return numbers.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * format the list of items by joining them with a line separator.
     *
     * @return String formatted of input by joining them with a line separator.
     */
    @Override
    public String formatPacks(List<String> list) {
        return list.stream().collect(Collectors.joining(System.lineSeparator()));
    }

}
