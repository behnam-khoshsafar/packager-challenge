package com.mobiquity.service.packer.impl;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Pack;
import com.mobiquity.service.packer.PackerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GSPackerServiceImplTest {

    private PackerService packerService;

    @BeforeEach
    void preparePackerService() {
        this.packerService = PackerServiceFactory.getInstance();
    }

    @Test
    void getOptimalItemsIndex_nullPack_throwsException() {
        String expectedMessage = "Pack is empty.";

        APIException exception = Assertions.assertThrows(APIException.class, () -> packerService.getOptimalItemsIndexes(null));

        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void getOptimalItemsIndex_packExceededTheMaxCapacity_throwsException() {
        String expectedMessage = "Pack capacity exceeded the max capacity. Pack capacity is";
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 34));
        Pack pack = new Pack(101, items);

        APIException exception = Assertions.assertThrows(APIException.class, () -> packerService.getOptimalItemsIndexes(pack));

        Assertions.assertTrue(exception.getMessage().startsWith(expectedMessage));
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void getOptimalItemsIndex_packCapacityEqualsToTheMaxCapacity_returnTheValidValue() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 34));
        Pack pack = new Pack(100, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(1, optimalItemsIndex.size());
        Assertions.assertEquals(1, optimalItemsIndex.get(0));
    }

    @Test
    void getOptimalItemsIndex_nullItems_throwsException() {
        String expectedMessage = "Pack item is empty.";
        Pack pack = new Pack(20, null);

        APIException exception = Assertions.assertThrows(APIException.class, () -> packerService.getOptimalItemsIndexes(pack));

        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void getOptimalItemsIndex_itemSizeExceededTheConstraint_throwsException() {
        String expectedMessage = "Pack item size exceeded the max item size. Item size is";
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 34));
        items.add(getItem(2, 10f, 34));
        items.add(getItem(3, 10f, 34));
        items.add(getItem(4, 10f, 34));
        items.add(getItem(5, 10f, 34));
        items.add(getItem(6, 10f, 34));
        items.add(getItem(7, 10f, 34));
        items.add(getItem(8, 10f, 34));
        items.add(getItem(9, 10f, 34));
        items.add(getItem(10, 10f, 34));
        items.add(getItem(11, 10f, 34));
        items.add(getItem(12, 10f, 34));
        items.add(getItem(13, 10f, 34));
        items.add(getItem(14, 10f, 34));
        items.add(getItem(15, 10f, 34));
        items.add(getItem(16, 10f, 34));
        Pack pack = new Pack(30, items);

        APIException exception = Assertions.assertThrows(APIException.class, () -> packerService.getOptimalItemsIndexes(pack));

        Assertions.assertTrue(exception.getMessage().startsWith(expectedMessage));
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void getOptimalItemsIndex_oneOfThemItemsExceededTheMaxWeight_throwsException() {
        String expectedMessage = "Weight of item exceeded the max item weight size. Max weight size is";
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 34));
        items.add(getItem(2, 101f, 34));
        Pack pack = new Pack(30, items);

        APIException exception = Assertions.assertThrows(APIException.class, () -> packerService.getOptimalItemsIndexes(pack));

        Assertions.assertTrue(exception.getMessage().startsWith(expectedMessage));
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void getOptimalItemsIndex_itemWeightEqualsToTheMaxWeight_returnValidValue() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 100f, 34));
        Pack pack = new Pack(100, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(1, optimalItemsIndex.size());
        Assertions.assertEquals(1, optimalItemsIndex.get(0));
    }

    @Test
    void getOptimalItemsIndex_oneOfThemItemsExceededTheMaxCost_throwsException() {
        String expectedMessage = "Cost of item exceeded the max item cost size. Max cost size is";
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 34));
        items.add(getItem(2, 10f, 101));
        Pack pack = new Pack(30, items);

        APIException exception = Assertions.assertThrows(APIException.class, () -> packerService.getOptimalItemsIndexes(pack));

        Assertions.assertTrue(exception.getMessage().startsWith(expectedMessage));
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void getOptimalItemsIndex_itemsCostEqualsToTheMaxCost_throwsException() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 100));
        Pack pack = new Pack(30, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(1, optimalItemsIndex.size());
        Assertions.assertEquals(1, optimalItemsIndex.get(0));
    }

    @Test
    void getOptimalItemsIndex_withDifferentItem_returnOneItem() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 53.38f, 45));
        items.add(getItem(2, 88.62f, 98));
        items.add(getItem(3, 78.48f, 3));
        items.add(getItem(4, 72.30f, 76));
        items.add(getItem(5, 30.18f, 9));
        items.add(getItem(6, 46.34f, 48));
        Pack pack = new Pack(81, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(1, optimalItemsIndex.size());
        Assertions.assertEquals(4, optimalItemsIndex.get(0));
    }

    @Test
    void getOptimalItemsIndex_itemWeightExceedThePackCapacity_returnEmptyList() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 15.3f, 34));
        Pack pack = new Pack(8, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(0, optimalItemsIndex.size());
    }

    @Test
    void getOptimalItemsIndex_withDifferentItem_returnTwoItem() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 85.31f, 29));
        items.add(getItem(2, 14.55f, 74));
        items.add(getItem(3, 3.98f, 16));
        items.add(getItem(4, 26.24f, 55));
        items.add(getItem(5, 63.69f, 52));
        items.add(getItem(6, 76.25f, 75));
        items.add(getItem(7, 60.02f, 74));
        items.add(getItem(8, 93.18f, 35));
        items.add(getItem(9, 89.95f, 78));
        Pack pack = new Pack(75, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(2, optimalItemsIndex.size());
        Assertions.assertEquals(2, optimalItemsIndex.get(0));
        Assertions.assertEquals(7, optimalItemsIndex.get(1));
    }

    @Test
    void getOptimalItemsIndex_itemsWithSamePrice_returnsTheLowestWeight() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 90.72f, 13));
        items.add(getItem(2, 33.80f, 40));
        items.add(getItem(3, 43.15f, 10));
        items.add(getItem(4, 37.97f, 16));
        items.add(getItem(5, 46.81f, 36));
        items.add(getItem(6, 48.77f, 79));
        items.add(getItem(7, 81.80f, 45));
        items.add(getItem(8, 19.36f, 79));
        items.add(getItem(9, 6.76f, 64));
        Pack pack = new Pack(56, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(2, optimalItemsIndex.size());
        Assertions.assertEquals(8, optimalItemsIndex.get(0));
        Assertions.assertEquals(9, optimalItemsIndex.get(1));
    }

    @Test
    void getOptimalItemsIndex_itemsWithSamePrice_returnsTheLowestWeightzxx() throws APIException {
        List<Item> items = new ArrayList<>();
        items.add(getItem(1, 80.00f, 80));
        items.add(getItem(2, 30.00f, 30));
        items.add(getItem(3, 30.00f, 30));
        items.add(getItem(4, 30.00f, 30));
        items.add(getItem(5, 33.80f, 40));
        Pack pack = new Pack(90, items);

        List<Integer> optimalItemsIndex = packerService.getOptimalItemsIndexes(pack);

        Assertions.assertEquals(3, optimalItemsIndex.size());
    }

    private Item getItem(int index, float weight, int cost) {
        return new Item(index, weight, cost);
    }

}