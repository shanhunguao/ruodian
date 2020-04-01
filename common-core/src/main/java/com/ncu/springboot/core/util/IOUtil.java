package com.ncu.springboot.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.ncu.springboot.core.constant.CharsetType;
import com.ncu.springboot.core.constant.type.FileType;
import com.ncu.springboot.core.constant.type.ImageType;
import com.ncu.springboot.core.jdkextends.NcuOptional;
import com.ncu.springboot.core.jdkextends.NcuStringBuilder;

public abstract class IOUtil {

    public static final byte[] EMPTY_BYTES = new byte[0];

    private static final int DEFAULT_SIZE = 4096;


    public static void close(Closeable... closeableAry) {
        for (Closeable closeable : closeableAry) {
            close(closeable);
        }
    }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static byte[] base64StringToBytes(String base64String) {
        if (StringUtils.isNotEmpty(base64String)) {
            return Base64.decodeBase64(base64String);
        } else {
            return EMPTY_BYTES;
        }
    }

    public static NcuOptional<byte[]> fileToBytes(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return streamToBytes(fis);
        } catch (Exception e) {
        } finally {
            close(fis);
        }
        return NcuOptional.empty();
    }


    public static NcuOptional<byte[]> streamToBytes(InputStream is) {
        return streamToBytes(is, false);
    }

    public static String streamToString(InputStream is) {
        return streamToString(is, CharsetType.CHARSET_UTF_8);
    }

    public static String streamToString(InputStream is, Charset charset) {
        return streamToBytes(is).get( bytes -> new String(bytes , charset) , StringUtil.EMPTY_STRING);
    }

    public static NcuOptional<byte[]> streamToBytes(InputStream is, boolean closeInputStream) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[DEFAULT_SIZE];
            int readedLen;
            while (-1 != (readedLen = is.read(buff))) {
                baos.write(buff, 0, readedLen);
            }
            return NcuOptional.of(baos.toByteArray());
        } catch (Exception e) {
        } finally {
            close(baos);
            if (closeInputStream) {
                close(is);
            }
        }
        return NcuOptional.empty();
    }

    public static String bytesToBase64String(NcuOptional<byte[]> optional) {
        return optional.get(bytes -> Base64.encodeBase64String(bytes) , StringUtil.EMPTY_STRING );
    }

    public static String fileToBase64String(File file) {
        return bytesToBase64String( fileToBytes(file) );
    }

    public static NcuOptional<String> getFileHeader(InputStream ips) {
        try {
            byte[] b = new byte[10];
            if (0 != ips.read(b, 0, b.length)) {
                return bytesToHexString(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NcuOptional.empty();
    }

    private static NcuOptional<String> bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( src == null ) {
            return NcuOptional.empty();
        }
        for(byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return NcuOptional.of(stringBuilder.toString());
    }

    public static String getFileType(File file) {
        try {
            return getFileType(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return StringUtil.EMPTY_STRING;
    }

    public static String getFileType(InputStream ips) {
        return getFileHeader(ips).get( header -> FileType.getByHeader(header) ,StringUtil.EMPTY_STRING );
    }

    public static String streamToBase64String(InputStream is) {
        return streamToBase64String(is, false);
    }

    public static String streamToBase64String(InputStream is, boolean closeInputStream) {
        return streamToBytes(is, closeInputStream).get( bytes -> Base64.encodeBase64String(bytes) , StringUtil.EMPTY_STRING );
    }

    public static boolean bytesToFile(byte[] bytes, File file) {
        if (null != bytes && null != file) {
            ByteArrayInputStream bais = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                return streamToFile(bais, file);
            } catch (Exception e) {
            } finally {
                close(bais);
            }
        }
        return false;
    }

    public static boolean streamToFile(InputStream is, File file) {
        return streamToFile(is, file, false);
    }

    public static boolean streamToFile(InputStream is, File file, boolean closeInputStream) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buff = new byte[DEFAULT_SIZE];
            int readedLen;
            while (-1 != (readedLen = is.read(buff))) {
                fos.write(buff, 0, readedLen);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
        } finally {
            close(fos);
            if (closeInputStream) {
                close(is);
            }
        }
        return false;
    }

    public static boolean base64StringToFile(String base64String, File file) {
        if (null != base64String) {
            byte[] bytes = base64StringToBytes(base64String);
            return bytesToFile(bytes, file);
        }
        return false;
    }

    public static InputStream bytesToStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static InputStream base64StringToStream(String base64String) {
        if (null != base64String) {
            byte[] bytes = base64StringToBytes(base64String);
            return new ByteArrayInputStream(bytes);
        }
        return null;
    }

    public static InputStream fileToStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static String fileToString(File file) {
        return fileToString(file, CharsetType.CHARSET_UTF_8);
    }

    public static String fileToString(File file, String charset) {
        return fileToString(file, Charset.forName(charset));
    }

    public static String fileToString(File file, Charset charset) {
        return fileToBytes(file).get( bytes -> new String(bytes, charset) , StringUtil.EMPTY_STRING );
    }

    public static boolean ensureDir(File dir) {
        if (!dir.exists()) {
            if (EnvironmentUtils.isOnWindows()) {
                boolean result = dir.mkdir();
            } else {
                return false;
            }
        }
        return true;
    }

    public static String ensureDir(String path, String outPath, String inPath) {
        if (path.endsWith(outPath)) {
            path = path.substring(0, path.length() - outPath.length());
        }
        path += inPath;
        ensureDir(new File(path));
        return StringUtil.processPath(path);
    }

    public static void delete(File file) {
        if (file.exists()) {
            boolean result = file.delete();
        }
    }

    public static void copy(InputStream ips, OutputStream fos, boolean flushFlag) {
        try {
            IOUtils.copy(ips, fos);
            if (flushFlag) {
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer streamLength(InputStream ips) {
        if (null == ips)
            return 0;
        return streamToBytes(ips).get( bytes -> bytes.length , 0 );
    }

    public static File[] listFile(File pathFile, FileFilter fileFilter) {
        return pathFile.listFiles(fileFilter);
    }

    public static NcuOptional<File> findFile(File pathFile, FileFilter fileFilter) {
        File[] fileAry = listFile(pathFile, fileFilter);
        if( CollectionUtil.isEmpty(fileAry) ) {
            return NcuOptional.empty();
        }
        if( fileAry.length > 1 ) {
        }
        return NcuOptional.of(fileAry[0]);
    }

    public static void flush(Flushable flush) {
        if (null != flush) {
            try {
                flush.flush();
            } catch (IOException e) {
            }
        }
    }

    public static void flush(Flushable... flushAry) {
        if( !CollectionUtil.isEmpty(flushAry) ) {
            Stream.of(flushAry).forEach(flushable -> flush(flushable));
        }
    }

    public static InputStream reset(InputStream ips) {
        try {
            ips.reset();
            mark(ips);
        } catch (Exception e) {
        }
        return ips;
    }

    public static InputStream mark(InputStream ips) {
        return mark(ips, Integer.MAX_VALUE);
    }

    public static InputStream mark(InputStream ips, Integer readlimit) {
        ips = new BufferedInputStream(ips);
        ips.mark(readlimit);
        return ips;
    }

    public static NcuOptional<BufferedImage> bytesToBufferedImage(byte[] b) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            return NcuOptional.of(ImageIO.read(in));
        } catch (Exception e) {
            return NcuOptional.empty();
        }
    }

    public static void copyImageBytes(byte[] imageBytes, ImageType type, OutputStream os) {
        copyImageBytesInInternal(imageBytes, type, os);
    }

    public static void copyImageBytes(byte[] imageBytes, ImageType type, File file) {
        copyImageBytesInInternal(imageBytes, type, file);
    }

    private static void copyImageBytesInInternal(byte[] imageBytes, ImageType type, Object fileOrOs) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(imageBytes);
            BufferedImage bi = ImageIO.read(bais);
            copyImageBytesInFileOrOs(bi, type, fileOrOs);
        } catch (Exception e) {
        } finally {
            IOUtil.close(bais);
        }
    }

    private static void copyImageBytesInFileOrOs(BufferedImage bi, ImageType type, Object fileOrOs) throws IOException {
        if (fileOrOs instanceof File) {
            ImageIO.write(bi, type.getName(), (File) fileOrOs);
        } else if (fileOrOs instanceof ImageOutputStream) {
            ImageIO.write(bi, type.getName(), (ImageOutputStream) fileOrOs);
        } else if (fileOrOs instanceof OutputStream) {
            ImageIO.write(bi, type.getName(), (OutputStream) fileOrOs);
        }
    }

    public static byte[] numberToBytes(Integer key) {
        byte[] bytes = new byte[0];
        ObjectOutputStream oos;
        ByteArrayOutputStream baos;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(key);
            bytes = baos.toByteArray();
        } catch (Exception e) {
        }
        return bytes;
    }

    public static void writeToFile(String source, File file) {
        OutputStream ops = null;
        try {
            ops = new FileOutputStream(file);
            byte[] b = source.getBytes(CharsetType.CHARSET_UTF_8);
            ops.write(b, 0, b.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(ops);
        }
    }

    public static String readReader(BufferedReader reader) {
    	NcuStringBuilder result = NcuStringBuilder.getInstance();
        try {
            String str;
            while ((str = reader.readLine()) != null) {
                result.append(str);
            }
        } catch (Exception e) {
        }
        return result.toString();
    }

    public static List<String> readByLine(String path) {
        return readByLine(path, CharsetType.CHARSET_UTF_8);
    }

    public static List<String> readByLine(String path, Charset charset) {
        BufferedReader reader = null;
        try {
            List<String> list = new ArrayList<>();
            String line;
            File file = new File(path);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            while (null != (line = reader.readLine())) {
                list.add(line);
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            IOUtil.close(reader);
        }
    }

    public static Properties readProperties(String path) {
        InputStream ips = null;
        Properties prop = new Properties();
        try {
            ips = new FileInputStream(path);
            prop.load(ips);
        } catch (Exception e) {
        } finally {
            IOUtil.close(ips);
        }
        return prop;
    }

}
