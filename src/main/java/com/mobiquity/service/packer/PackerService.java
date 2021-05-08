package com.mobiquity.service.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Pack;

import java.util.List;

public interface PackerService {

    /**
     * returns optimal item in pack based on the concrete class.
     * we can implement multiple packer service and used them by strategy pattern.
     *
     * @param pack Includes pack max capacity and list of items with their costs and weight.
     * @return list of indexes of optimal items in pack.
     */
    List<Integer> getOptimalItemsIndexes(Pack pack) throws APIException;

}
