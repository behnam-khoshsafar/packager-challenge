package com.mobiquity.service.parser;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Pack;

import java.util.List;

public interface ParserService {

    /**
     * Parse given input file with problem definition to a list of Packs
     *
     * @param filePath absolute path to the file that contains list of packs and items.
     * @return List of pack based on the data in input file.
     * @throws APIException when file content can not be parsed.
     */
    List<Pack> parse(String filePath) throws APIException;


}
