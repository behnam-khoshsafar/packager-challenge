package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

class PackerTest {

    @Test
    void pack_nullFilePath_throwsException() {
        String expectedMessage = "File path is empty.";
        APIException exception = Assertions.assertThrows(APIException.class, () -> Packer.pack(null));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void pack_emptyFilePath_throwsException() {
        String expectedMessage = "File path is empty.";
        APIException exception = Assertions.assertThrows(APIException.class, () -> Packer.pack(" "));
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertNull(exception.getCause());
    }

    @Test
    void pack_validFileAddress_returnPacksListSuccessfully() throws APIException {
        File validInputData = new File(
                Objects.requireNonNull(PackerTest.class
                        .getClassLoader()
                        .getResource("example_input")).getFile());

        String pack = Packer.pack(validInputData.getAbsolutePath());

        Assertions.assertEquals("4\n" +
                "-\n" +
                "2,7\n" +
                "8,9", pack);
    }
}