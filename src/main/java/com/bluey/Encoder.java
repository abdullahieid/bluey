package com.bluey;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class Encoder {
    public static String encodeImage(String path) throws IOException {
        byte[] fileContent = new FileInputStream(path).readAllBytes();
        System.out.println("size:: " + fileContent.length);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }
}
