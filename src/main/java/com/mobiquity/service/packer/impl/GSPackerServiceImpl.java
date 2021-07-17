package com.mobiquity.service.packer.impl;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Pack;
import com.mobiquity.service.packer.PackerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete class to solve package challenge using greedy algorithm to reach maximum cost and lowest weight in pack.
 */
class GSPackerServiceImpl implements PackerService {

    public static final int ITEM_MAX_WEIGHT = 100;
    public static final int ITEM_MAX_COST = 100;
    public static final int PACK_MAX_CAPACITY = 100;
    public static final int PACK_MAX_ITEMS_SIZE = 15;

    GSPackerServiceImpl() {
    }

    @Override
    public List<Integer> getOptimalItemsIndexes(Pack pack) throws APIException {
        validatePack(pack);
        return getOptimalItemsIndexesSorted(pack);
    }

    private void validatePack(Pack pack) throws APIException {
        if (pack == null)
            throw new APIException("Pack is empty.");
        validatePackWeight(pack);
        validatePackItem(pack);
    }

    private void validatePackWeight(Pack pack) throws APIException {
        if (pack.getMaxCapacity() > PACK_MAX_CAPACITY)
            throw new APIException("Pack capacity exceeded the max capacity. Pack capacity is " +
                    pack.getMaxCapacity() + " and max capacity is " + PACK_MAX_CAPACITY);
    }

    private void validatePackItem(Pack pack) throws APIException {
        if (pack.getItems() == null)
            throw new APIException("Pack item is empty.");
        validateItemSize(pack);
        validateItemWeight(pack);
        validateItemCost(pack);
    }

    private void validateItemSize(Pack pack) throws APIException {
        if (pack.getItems().size() > PACK_MAX_ITEMS_SIZE)
            throw new APIException("Pack item size exceeded the max item size. Item size is  " +
                    pack.getItems().size() + " and max item size is " + PACK_MAX_ITEMS_SIZE);
    }

    private void validateItemWeight(Pack pack) throws APIException {
        boolean isInvalidWeightExist = pack.getItems().stream().anyMatch(item -> item.getWeight() > ITEM_MAX_WEIGHT);
        if (isInvalidWeightExist)
            throw new APIException("Weight of item exceeded the max item weight size. Max weight size is " + ITEM_MAX_WEIGHT);
    }

    private void validateItemCost(Pack pack) throws APIException {
        boolean isInvalidCostExist = pack.getItems().stream().anyMatch(item -> item.getCost() > ITEM_MAX_COST);
        if (isInvalidCostExist)
            throw new APIException("Cost of item exceeded the max item cost size. Max cost size is " + ITEM_MAX_COST);
    }


    /**
     * @return list of indexes of optimal items in pack.
     */
    private List<Integer> getOptimalItemsIndexesSorted(Pack pack) {
        Item[] items = new Item[pack.getItems().size()];
        List<Item> optimalIndexes = getOptimalItemsRecursive(pack.getItems().toArray(items),
                pack.getMaxCapacity(),
                pack.getItems().size());
        return sortItemsIndexes(optimalIndexes.stream().map(Item::getIndex).collect(Collectors.toList()));
    }

    private List<Item> getOptimalItemsRecursive(Item[] items, double capacity, int length) {
        if (length == 0 || capacity == 0)
            return Collections.emptyList();

        // If capacity of the nth item is more than the capacity, then this item cannot be included in the optimal solution
        Item item = items[length - 1];
        if (item.getWeight() > capacity) {
            return getOptimalItemsRecursive(items, capacity, length - 1);
        }

        // Find optimal items consider that the n(th) item is in the selected items
        List<Item> including = new ArrayList<>();
        Collections.addAll(including, item);
        including.addAll(getOptimalItemsRecursive(items, capacity - item.getWeight(), length - 1));

        // Find optimal items consider that the n(th) item is not in the selected items
        List<Item> notIncluding = getOptimalItemsRecursive(items, capacity, length - 1);

        return getOptimalItems(including, notIncluding);

    }

    private List<Item> getOptimalItems(List<Item> including, List<Item> notIncluding) {
        if (isPriceEqual(including, notIncluding))
            return getItemsWithLowerTotalWeight(including, notIncluding);
        return getItemsWithHighestTotalCost(including, notIncluding);
    }

    private boolean isPriceEqual(List<Item> including, List<Item> notIncluding) {
        return including.stream().mapToDouble(Item::getCost).sum() == notIncluding.stream().mapToDouble(Item::getCost).sum();
    }

    private List<Item> getItemsWithLowerTotalWeight(List<Item> including, List<Item> notIncluding) {
        if (including.stream().mapToDouble(Item::getWeight).sum() < notIncluding.stream().mapToDouble(Item::getWeight).sum())
            return including;
        return notIncluding;
    }

    private List<Item> getItemsWithHighestTotalCost(List<Item> including, List<Item> notIncluding) {
        if (including.stream().mapToDouble(Item::getCost).sum() > notIncluding.stream().mapToDouble(Item::getCost).sum())
            return including;
        return notIncluding;
    }

    /**
     * sort indexes ascending.
     *
     * @return sorted list of indexes.
     */
    private List<Integer> sortItemsIndexes(List<Integer> indexes) {
        if (indexes.size() > 1)
            return indexes.stream().sorted().collect(Collectors.toList());
        return indexes;
    }
}
