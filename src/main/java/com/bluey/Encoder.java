package com.bluey;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class Encoder {
    public static String encodeImage(String path) throws IOException {
        byte[] fileContent = new FileInputStream(path).readAllBytes();
        System.out.println("size:: " + fileContent.length);
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static byte[] makeBytes(String path) throws IOException {
        File fnew = new File(path);
        BufferedImage originalImage = ImageIO.read(fnew);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage,"jpg", baos);

        return baos.toByteArray();
    }
}
