package com.mobiquity.service.parser.impl;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Pack;
import com.mobiquity.service.parser.ParserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ParserServiceImpl implements ParserService {

    private final Pattern packPattern;
    private final Pattern itemPattern;

    ParserServiceImpl() {
        String itemRegex = "\\((?<index>\\d+),(?<weight>\\d+\\.\\d+),\u20AC(?<cost>\\d+)\\)";
        String packRegex = String.format("(\\d+) : ((%s\\s*)+)", itemRegex);
        this.packPattern = Pattern.compile(packRegex);
        this.itemPattern = Pattern.compile(itemRegex);
    }

    @Override
    public List<Pack> parse(String filePath) throws APIException {
        validateFilePath(filePath);
        return getPacks(getFilePath(filePath));
    }

    private void validateFilePath(String filePath) throws APIException {
        if (filePath == null || filePath.isBlank())
            throw new APIException("File path is empty");
        checkFileExist(getFilePath(filePath));
    }

    private Path getFilePath(String filePath) throws APIException {
        return Paths.get(filePath);
    }

    private void checkFileExist(Path filePath) throws APIException {
        if (!Files.exists(filePath))
            throw new APIException("File does not exist for " + filePath);
    }

    private List<Pack> getPacks(Path filePath) throws APIException {
        List<Pack> packs = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(filePath)) {
                packs.add(lineToPack(line));
            }
        } catch (IOException e) {
            throw new APIException("An I/O error occurred reading from the file", e);
        }
        return packs;
    }

    private Pack lineToPack(String line) throws APIException {
        validateLinePattern(line);
        String[] lineSplitByColon = line.split(" : ");
        int packCapacity = getPackCapacity(lineSplitByColon[0]);
        List<Item> items = getItems(lineSplitByColon[1]);
        return new Pack(packCapacity, items);
    }

    private void validateLinePattern(String line) throws APIException {
        Matcher matcher = packPattern.matcher(line);
        if (!matcher.matches())
            throw new APIException("Input line not match to the pattern. line -> " + line);
    }

    private int getPackCapacity(String capacity) {
        return Integer.parseInt(capacity);
    }

    private List<Item> getItems(String itemsString) {
        Matcher itemMatcher = itemPattern.matcher(itemsString);
        List<Item> items = new ArrayList<>();
        while (itemMatcher.find()) {
            Item triplet = new Item(
                    Integer.parseInt(itemMatcher.group("index")),
                    Float.parseFloat(itemMatcher.group("weight")),
                    Integer.parseInt(itemMatcher.group("cost")));
            items.add(triplet);
        }
        return items;
    }

}
