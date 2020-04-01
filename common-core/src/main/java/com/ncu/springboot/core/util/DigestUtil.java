package com.ncu.springboot.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.ncu.springboot.core.constant.CharsetType;

public class DigestUtil {

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static MessageDigest getMD5Digest() {
        return getDigest("MD5");
    }

    private static MessageDigest getSHA1Digest() {
        return getDigest("SHA-1");
    }

    private static MessageDigest getSHA256Digest() {
        return getDigest("SHA-256");
    }

    private static MessageDigest getSHA512Digest() {
        return getDigest("SHA-512");
    }

    public static String MD5(String source, String salt) {
        return MD5(source + salt);
    }

    public static String MD5(String content) {
        return MD5(content.getBytes(CharsetType.CHARSET_UTF_8));
    }

    public static String MD5(byte[] data) {
        byte[] digestData = getMD5Digest().digest(data);
        return Hex.encodeHexString(digestData);
    }

    public static byte[] MD5WithBuffer(String content) {
        return MD5WithBuffer(content.getBytes(CharsetType.CHARSET_UTF_8));
    }

    public static byte[] MD5WithBuffer(byte[] data) {
        return getMD5Digest().digest(data);
    }

    public static String SHA1(byte[] data) {
        byte[] digestData = getSHA1Digest().digest(data);
        return Hex.encodeHexString(digestData);
    }

    public static String SHA256(byte[] data) {
        byte[] digestData = getSHA256Digest().digest(data);
        return Hex.encodeHexString(digestData);
    }

    public static String SHA512(byte[] data) {
        byte[] digestData = getSHA512Digest().digest(data);
        return Hex.encodeHexString(digestData);
    }

    public static String digest(String algorithm, byte[] data) {
        byte[] digestData = getDigest(algorithm).digest(data);
        return Hex.encodeHexString(digestData);
    }

}
