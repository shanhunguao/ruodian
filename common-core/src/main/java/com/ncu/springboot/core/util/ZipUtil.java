package com.ncu.springboot.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class ZipUtil {

    private static final int BUFFER_LENGTH = 2048;

    public static byte[] gzip(byte[] ungzipped) {
        GZIPOutputStream gos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            gos = new GZIPOutputStream(baos);
            gos.write(ungzipped, 0, ungzipped.length);
            gos.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            return IOUtil.EMPTY_BYTES;
        } finally {
            IOUtil.close(gos);
            IOUtil.close(baos);
        }
    }

    public static byte[] ungzip(byte[] gzipped) {
        return ungzip(gzipped, false);
    }

    public static byte[] ungzip(byte[] gzipped, boolean giveRawIfFailed) {
        GZIPInputStream gis = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        try {
            byte[] buffer = new byte[BUFFER_LENGTH];
            bais = new ByteArrayInputStream(gzipped);
            gis = new GZIPInputStream(bais, BUFFER_LENGTH);
            baos = new ByteArrayOutputStream(gzipped.length);
            int readedLength = 0;
            while (-1 != (readedLength = gis.read(buffer))) {
                baos.write(buffer, 0, readedLength);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            if (giveRawIfFailed) {
                return gzipped;
            } else {
                return IOUtil.EMPTY_BYTES;
            }
        } finally {
            IOUtil.close(bais);
            IOUtil.close(gis);
            IOUtil.close(baos);
        }
    }

}
