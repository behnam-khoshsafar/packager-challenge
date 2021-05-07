package com.mobiquity.service.parser.impl;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Pack;
import com.mobiquity.service.parser.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

class ParserServiceImplTest {

    private ParserService parserService;

    @BeforeEach
    void prepareParserServer() {
        this.parserService = ParserServiceFactory.getInstance();
    }

    @Test
    void parse_nullFilePath_throwsException() {
        String expectedMessage = "File path is empty";
        APIException exception = Assertions.assertThrows(APIException.class, () -> parserService.parse(null));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void parse_emptyFilePath_throwsException() {
        String expectedMessage = "File path is empty";
        APIException exception = Assertions.assertThrows(APIException.class, () -> parserService.parse(" "));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void parse_wrongFilePath_throwsException() {
        String expectedMessage = "File does not exist for wrong-file-path";
        APIException exception = Assertions.assertThrows(APIException.class, () -> parserService.parse("wrong-file-path"));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    /*
     * One of the opening parentheses misses for item in the file
     * */
    @Test
    void parse_invalidPatternOnOneOfLines_throwsException() throws APIException {
        String expectedMessage = "Input line not match to the pattern. line -> ";
        File fileWithInvalidPatternInlines = new File(
                Objects.requireNonNull(ParserServiceImplTest.class
                        .getClassLoader()
                        .getResource("example_input_with_invalid_pattern_in_line")).getFile());
        APIException exception = Assertions.assertThrows(APIException.class, () -> parserService.parse(fileWithInvalidPatternInlines.getAbsolutePath()));
        Assertions.assertTrue(exception.getMessage().startsWith(expectedMessage));
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void parse_validFileAddress_returnPacksListSuccessfully() throws APIException {
        File validInputData = new File(
                Objects.requireNonNull(ParserServiceImplTest.class
                        .getClassLoader()
                        .getResource("example_input")).getFile());

        List<Pack> packs = parserService.parse(validInputData.getAbsolutePath());

        int firstPackCapacity = 81;
        int secondPackCapacity = 8;
        int thirdPackCapacity = 75;
        int fourthPackCapacity = 56;
        Assertions.assertEquals(4, packs.size());
        packs.forEach(pack -> {
            if (pack.getMaxCapacity() == firstPackCapacity) {
                validateFirstPackItem(pack);
            } else if (pack.getMaxCapacity() == secondPackCapacity) {
                validateSecondPackItem(pack);
            } else if (pack.getMaxCapacity() == thirdPackCapacity) {
                validateThirdPackItem(pack);
            } else if (pack.getMaxCapacity() == fourthPackCapacity) {
                validateFourthPackItem(pack);
            } else {
                Assertions.fail();
            }
        });
    }

    private void validateFirstPackItem(Pack pack) {
        Assertions.assertEquals(6, pack.getItems().size());
        AtomicInteger count = new AtomicInteger();
        pack.getItems().forEach(item -> {
            if (count.get() == 0) {
                validateItem(item, getItem(1, 53.38f, 45));
            } else if (count.get() == 1) {
                validateItem(item, getItem(2, 88.62f, 98));
            } else if (count.get() == 2) {
                validateItem(item, getItem(3, 78.48f, 3));
            } else if (count.get() == 3) {
                validateItem(item, getItem(4, 72.30f, 76));
            } else if (count.get() == 4) {
                validateItem(item, getItem(5, 30.18f, 9));
            } else if (count.get() == 5) {
                validateItem(item, getItem(6, 46.34f, 48));
            } else {
                Assertions.fail();
            }
            count.getAndIncrement();
        });
    }

    private void validateSecondPackItem(Pack pack) {
        Assertions.assertEquals(1, pack.getItems().size());
        AtomicInteger count = new AtomicInteger();
        pack.getItems().forEach(item -> {
            if (count.get() == 0) {
                validateItem(item, getItem(1, 15.3f, 34));
            } else {
                Assertions.fail();
            }
            count.getAndIncrement();
        });
    }

    private void validateThirdPackItem(Pack pack) {
        Assertions.assertEquals(9, pack.getItems().size());
        AtomicInteger count = new AtomicInteger();
        pack.getItems().forEach(item -> {
            if (count.get() == 0) {
                validateItem(item, getItem(1, 85.31f, 29));
            } else if (count.get() == 1) {
                validateItem(item, getItem(2, 14.55f, 74));
            } else if (count.get() == 2) {
                validateItem(item, getItem(3, 3.98f, 16));
            } else if (count.get() == 3) {
                validateItem(item, getItem(4, 26.24f, 55));
            } else if (count.get() == 4) {
                validateItem(item, getItem(5, 63.69f, 52));
            } else if (count.get() == 5) {
                validateItem(item, getItem(6, 76.25f, 75));
            } else if (count.get() == 6) {
                validateItem(item, getItem(7, 60.02f, 74));
            } else if (count.get() == 7) {
                validateItem(item, getItem(8, 93.18f, 35));
            } else if (count.get() == 8) {
                validateItem(item, getItem(9, 89.95f, 78));
            } else {
                Assertions.fail();
            }
            count.getAndIncrement();
        });
    }

    private void validateFourthPackItem(Pack pack) {
        Assertions.assertEquals(9, pack.getItems().size());
        AtomicInteger count = new AtomicInteger();
        pack.getItems().forEach(item -> {
            if (count.get() == 0) {
                validateItem(item, getItem(1, 90.72f, 13));
            } else if (count.get() == 1) {
                validateItem(item, getItem(2, 33.80f, 40));
            } else if (count.get() == 2) {
                validateItem(item, getItem(3, 43.15f, 10));
            } else if (count.get() == 3) {
                validateItem(item, getItem(4, 37.97f, 16));
            } else if (count.get() == 4) {
                validateItem(item, getItem(5, 46.81f, 36));
            } else if (count.get() == 5) {
                validateItem(item, getItem(6, 48.77f, 79));
            } else if (count.get() == 6) {
                validateItem(item, getItem(7, 81.80f, 45));
            } else if (count.get() == 7) {
                validateItem(item, getItem(8, 19.36f, 79));
            } else if (count.get() == 8) {
                validateItem(item, getItem(9, 6.76f, 64));
            } else {
                Assertions.fail();
            }
            count.getAndIncrement();
        });
    }

    private Item getItem(int index, float weight, int cost) {
        return new Item(index, weight, cost);
    }

    private void validateItem(Item actualItem, Item expectedItem) {
        Assertions.assertEquals(expectedItem.getIndex(), actualItem.getIndex());
        Assertions.assertEquals(expectedItem.getWeight(), actualItem.getWeight());
        Assertions.assertEquals(expectedItem.getCost(), actualItem.getCost());
    }


}