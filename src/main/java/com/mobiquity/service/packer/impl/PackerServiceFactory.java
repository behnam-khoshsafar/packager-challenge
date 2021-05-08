package com.mobiquity.service.packer.impl;

import com.mobiquity.service.packer.PackerService;

/**
 * Factory class for creation of packer service.
 */
public class PackerServiceFactory {

    private static PackerService packerService;

    public static PackerService getInstance() {
        if (packerService == null) {
            synchronized (CumulativePackerServiceImpl.class) {
                if (packerService == null)
                    packerService = new CumulativePackerServiceImpl();
            }
        }
        return packerService;
    }

}
