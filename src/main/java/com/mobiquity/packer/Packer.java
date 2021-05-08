package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Pack;
import com.mobiquity.service.formatter.FormatterService;
import com.mobiquity.service.formatter.impl.FormatterServiceFactory;
import com.mobiquity.service.packer.PackerService;
import com.mobiquity.service.packer.impl.PackerServiceFactory;
import com.mobiquity.service.parser.ParserService;
import com.mobiquity.service.parser.impl.ParserServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        validateFilePath(filePath);
        FormatterService formatterService = FormatterServiceFactory.getInstance();
        List<String> packsItems = getPacksOptimalItems(filePath, formatterService);
        return formatterService.formatPacks(packsItems);
    }

    private static void validateFilePath(String filePath) throws APIException {
        if (filePath == null || filePath.isBlank())
            throw new APIException("File path is empty.");
    }

    private static List<String> getPacksOptimalItems(String filePath, FormatterService formatterService) throws APIException {
        PackerService packerService = PackerServiceFactory.getInstance();
        List<String> packsItems = new ArrayList<>();

        List<Pack> packs = getPacks(filePath);
        for (Pack pack : packs) {
            List<Integer> optimalItemsIndexes = packerService.getOptimalItemsIndexes(pack);
            packsItems.add(formatterService.formatItems(optimalItemsIndexes));
        }
        return packsItems;
    }

    private static List<Pack> getPacks(String filePath) throws APIException {
        ParserService parserService = ParserServiceFactory.getInstance();
        return parserService.parse(filePath);
    }
}
