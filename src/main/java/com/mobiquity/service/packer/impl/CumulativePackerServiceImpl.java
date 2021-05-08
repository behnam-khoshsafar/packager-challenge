package com.mobiquity.service.packer.impl;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Pack;
import com.mobiquity.service.packer.PackerService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete class to solve package challenge using cumulative approach to reach maximum cost and lowest weight in pack.
 */
class CumulativePackerServiceImpl implements PackerService {

    public static final int ITEM_MAX_WEIGHT = 100;
    public static final int ITEM_MAX_COST = 100;
    public static final int PACK_MAX_CAPACITY = 100;
    public static final int PACK_MAX_ITEMS_SIZE = 15;

    CumulativePackerServiceImpl() {
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
        List<Item> sortedItemsInPackWeightRange = getItemsInPackWeightRangeSorted(pack);
        List<Integer> optimalIndexes = getOptimalItemsIndexes(pack, sortedItemsInPackWeightRange);
        return sortItemsIndexes(optimalIndexes);
    }

    /**
     * First remove items that is not in range of pack max capacity.
     * then sorts them based on their cost and weight.
     *
     * @return list of item that have valid range of weight and sorted using by cost and weight.
     */
    private List<Item> getItemsInPackWeightRangeSorted(Pack pack) {
        return pack.getItems().stream()
                .filter(item -> item.getWeight() <= pack.getMaxCapacity())
                .sorted(getComparator())
                .collect(Collectors.toList());
    }

    /**
     * Generate a comparator that we can use it to compares items based on their cost and sorts them descending.
     * if costs are equal then compare them using weight and sort these items ascending.
     *
     * @return comparator.
     */
    private Comparator<Item> getComparator() {
        return Comparator.comparing(Item::getCost).reversed()
                .thenComparing(Item::getWeight);
    }

    /**
     * First remove items that is not in range of pack max capacity.
     * then sorts them based on their cost and weight.
     *CumulativePackerServiceImpl.java
     * @return list of item that have valid range of weight and sorted using by cost and weight.
     */
    private List<Integer> getOptimalItemsIndexes(Pack pack, List<Item> sortedItemsInPackWeightRange) {
        List<Integer> optimalIndexes = new ArrayList<>();
        float optimalTotalItemWeight = 0f;
        for (Item item : sortedItemsInPackWeightRange) {
            float totalWeight = Float.sum(optimalTotalItemWeight, item.getWeight());
            if (totalWeight < pack.getMaxCapacity()) {
                optimalTotalItemWeight = totalWeight;
                optimalIndexes.add(item.getIndex());
            }
        }
        return optimalIndexes;
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
