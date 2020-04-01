package com.ncu.springboot.core.util;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.ncu.springboot.core.constant.type.ImageType;
import com.ncu.springboot.core.jdkextends.NcuOptional;

public class ImageUtil {

    private static final byte[] HEAD_JPEG = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0};

    private static final byte[] HEAD_PNG = {(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47};

    private static final byte[] HEAD_GIF = {(byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38};

    private static final byte[] HEAD_BMP = {(byte) 0x42, (byte) 0x4D};

    private static String[] supportedImageTypeNames;

    static {
        init();
    }

    private static void init() {
        ImageType[] imageTypes = ImageType.values();
        supportedImageTypeNames = new String[imageTypes.length];
        for (int i = 0; i < imageTypes.length; i++) {
            supportedImageTypeNames[i] = imageTypes[i].getName();
        }
    }

    public static String[] getSupportedImageTypeNames() {
        return CollectionUtil.clone(supportedImageTypeNames);
    }

    public static NcuOptional<ImageType> getImageType(byte[] imageBytes) {
        ImageType result = null;
        if (null != imageBytes) {
            if (startsWith(imageBytes, HEAD_JPEG)) {
                result = ImageType.JPG;
            } else if (startsWith(imageBytes, HEAD_PNG)) {
                result = ImageType.PNG;
            } else if (startsWith(imageBytes, HEAD_GIF)) {
                result = ImageType.GIF;
            } else if (startsWith(imageBytes, HEAD_BMP)) {
                result = ImageType.BMP;
            }
        }
        return NcuOptional.of(result);
    }

    private static boolean startsWith(byte[] testBytes, byte[] head) {
        if (testBytes.length >= head.length) {
            for (int i = 0; i < head.length; i++) {
                if (testBytes[i] != head[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean writeBytes(byte[] imageBytes, OutputStream os) {
        return getImageType(imageBytes).get( type -> {
            IOUtil.copyImageBytes(imageBytes, type, os);
            return true;
        } , false );
    }

    public static BufferedImage imageBytesToBufferedImage(byte[] imageBytes) {
        if (null != imageBytes) {
            ByteArrayInputStream bais = null;
            try {
                bais = new ByteArrayInputStream(imageBytes);
                return ImageIO.read(bais);
            } catch (Exception e) {
            } finally {
                IOUtil.close(bais);
            }
        }
        return null;
    }

    public static boolean writeBytesToFile(byte[] imageBytes, String filePathWithoutSuffix) {
        return getImageType(imageBytes).get( type -> {
            String filePath = filePathWithoutSuffix + '.' + type.getSuffix();
            IOUtil.copyImageBytes(imageBytes, type, new File(filePath));
            return true;
        } , false);
    }

}
