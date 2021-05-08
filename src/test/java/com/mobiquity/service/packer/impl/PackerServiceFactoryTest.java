package com.mobiquity.service.packer.impl;

import com.mobiquity.service.packer.PackerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackerServiceFactoryTest {

    @Test
    void onlyOnceInstanceCreatedWhenGetInstance() {

        PackerService packerServiceFirstInstance = PackerServiceFactory.getInstance();
        PackerService packerServiceSecondInstance = PackerServiceFactory.getInstance();

        assertEquals(packerServiceFirstInstance, packerServiceSecondInstance);
    }

}