package com.mobiquity.service.formatter.impl;

import com.mobiquity.service.formatter.FormatterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FormatterServiceImplTest {

    private FormatterService formatterService;

    @BeforeEach
    void prepareFormatterService() {
        this.formatterService = FormatterServiceFactory.getInstance();
    }

    @Test
    void formatItems_emptyList_returnDash(){
        String formattedItem = formatterService.formatItems(new ArrayList<>());
        Assertions.assertEquals("-",formattedItem);
    }

    @Test
    void formatItems_nullList_returnDash(){
        String formattedItem = formatterService.formatItems(null);
        Assertions.assertEquals("-",formattedItem);
    }

    @Test
    void formatItems_withValidData_returnFormattedStringJoinByComma(){
        List<Integer> items=new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(3);
        String formattedItem = formatterService.formatItems(items);
        Assertions.assertEquals("1,2,3",formattedItem);
    }

    @Test
    void formatPacks_withValidData_returnFormattedStringJoinByComma(){
        List<String> items=new ArrayList<>();
        items.add("1,2");
        items.add("3,4");
        items.add("3,6");
        String formatPacks = formatterService.formatPacks(items);
        Assertions.assertEquals("1,2\n3,4\n3,6",formatPacks);
    }


}