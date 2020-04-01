package com.ncu.springboot.core.util;

import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

import com.ncu.springboot.core.jdkextends.NcuOptional;

public class CryptoUtil {

    public static final String KEY_ALG_DES = "DES";

    public static final String KEY_ALG_TRIDES = "DESede";

    public static final String KEY_ALG_BLOWFISH = "Blowfish";

    public static final String DEFAULT_CIPHER_ALG_DES_ECB = "DES/ECB/PKCS5Padding";

    public static final String DEFAULT_CIPHER_ALG_TRIDES_ECB = "DESede/ECB/PKCS5Padding";

    public static final String DEFAULT_CIPHER_ALG_DES_CBC = "DES/CBC/PKCS5Padding";

    public static final String DEFAULT_CIPHER_ALG_TRIDES_CBC = "DESede/CBC/PKCS5Padding";

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static String CRYPT_KEY = "agvbcshsagvBCSHSagvbcsHS";

    private static String CRYPT_IV = "13264578";

    private static byte[] CRYPT_KEY_BYTES = buildTriDesKey(CRYPT_KEY);

    private static byte[] CRYPT_IV_BYTES = CRYPT_IV.getBytes(CHARSET);

    /**
     * 创建DES密钥
     *
     * @param key 密钥字节数组
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey createDesKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        // 创建密钥规则
        DESKeySpec keySpec = new DESKeySpec(key);
        // 创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALG_DES);
        // 生成密钥
        return keyFactory.generateSecret(keySpec);
    }

    /**
     * 创建3DES密钥
     *
     * @param key 密钥字节数组
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey createTriDesKey(byte[] key) throws GeneralSecurityException {
        // 创建密钥规则
        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        // 创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALG_TRIDES);
        // 生成密钥
        return keyFactory.generateSecret(keySpec);
    }

    /**
     * 加密
     *
     * @param data      数据
     * @param key       密钥
     * @param cipherAlg 加密算法，如<code>DESede/ECB/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] data, Key key, String cipherAlg) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlg);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data      数据
     * @param key       密钥
     * @param cipherAlg 加密算法，如<code>DESede/ECB/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] data, Key key, String cipherAlg) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlg);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data         数据
     * @param key          密钥
     * @param algParamSpec 算法参数规则，如{@link IvParameterSpec}
     * @param cipherAlg    加密算法，如<code>DESede/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] data, Key key, AlgorithmParameterSpec algParamSpec, String cipherAlg)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlg);
        cipher.init(Cipher.ENCRYPT_MODE, key, algParamSpec);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data         数据
     * @param key          密钥
     * @param algParamSpec 算法参数规则，如{@link IvParameterSpec}
     * @param cipherAlg    加密算法，如<code>DESede/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] data, Key key, AlgorithmParameterSpec algParamSpec, String cipherAlg)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlg);
        cipher.init(Cipher.DECRYPT_MODE, key, algParamSpec);
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data      数据
     * @param key       密钥
     * @param algParams 算法参数
     * @param cipherAlg 加密算法，如<code>DESede/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] data, Key key, AlgorithmParameters algParams, String cipherAlg)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlg);
        cipher.init(Cipher.ENCRYPT_MODE, key, algParams);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data      数据
     * @param key       密钥
     * @param algParams 算法参数
     * @param cipherAlg 加密算法，如<code>DESede/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] data, Key key, AlgorithmParameters algParams, String cipherAlg)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(cipherAlg);
        cipher.init(Cipher.DECRYPT_MODE, key, algParams);
        return cipher.doFinal(data);
    }

    /**
     * DES加密
     *
     * @param data      数据
     * @param key       密钥
     * @param cipherAlg 加密算法，如<code>DES/ECB/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptDes(byte[] data, byte[] key, String cipherAlg) throws GeneralSecurityException {
        SecretKey keyObj = createDesKey(key);
        return encrypt(data, keyObj, cipherAlg);
    }

    /**
     * DES解密
     *
     * @param data      数据
     * @param key       密钥
     * @param cipherAlg 加密算法，如<code>DES/ECB/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptDes(byte[] data, byte[] key, String cipherAlg) throws GeneralSecurityException {
        SecretKey keyObj = createDesKey(key);
        return decrypt(data, keyObj, cipherAlg);
    }

    /**
     * DES加密
     *
     * @param data      数据
     * @param key       密钥
     * @param iv        向量
     * @param cipherAlg 加密算法，如<code>DES/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptDesWithIV(byte[] data, byte[] key, byte[] iv, String cipherAlg)
            throws GeneralSecurityException {
        SecretKey keyObj = createDesKey(key);
        return encrypt(data, keyObj, new IvParameterSpec(iv), cipherAlg);
    }

    /**
     * DES解密
     *
     * @param data      数据
     * @param key       密钥
     * @param iv        向量
     * @param cipherAlg 加密算法，如<code>DES/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptDesWithIV(byte[] data, byte[] key, byte[] iv, String cipherAlg)
            throws GeneralSecurityException {
        SecretKey keyObj = createDesKey(key);
        return decrypt(data, keyObj, new IvParameterSpec(iv), cipherAlg);
    }

    /**
     * DES加密，使用加密算法<code>DES/ECB/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptDes(byte[] data, byte[] key) throws GeneralSecurityException {
        return encryptDes(data, key, DEFAULT_CIPHER_ALG_DES_ECB);
    }

    /**
     * DES解密，使用加密算法<code>DES/ECB/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptDes(byte[] data, byte[] key) throws GeneralSecurityException {
        return decryptDes(data, key, DEFAULT_CIPHER_ALG_DES_ECB);
    }

    /**
     * DES加密，使用加密算法<code>DES/CBC/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @param iv   向量
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptDesWithIV(byte[] data, byte[] key, byte[] iv) throws GeneralSecurityException {
        return encryptDesWithIV(data, key, iv, DEFAULT_CIPHER_ALG_DES_CBC);
    }

    /**
     * DES解密，使用加密算法<code>DES/CBC/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @param iv   向量
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptDesWithIV(byte[] data, byte[] key, byte[] iv) throws GeneralSecurityException {
        return decryptDesWithIV(data, key, iv, DEFAULT_CIPHER_ALG_DES_CBC);
    }

    /**
     * 3DES加密
     *
     * @param data      数据
     * @param key       密钥
     * @param cipherAlg 加密算法，如<code>DESede/ECB/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptTriDes(byte[] data, byte[] key, String cipherAlg) throws GeneralSecurityException {
        SecretKey keyObj = createTriDesKey(key);
        return encrypt(data, keyObj, cipherAlg);
    }

    /**
     * 3DES解密
     *
     * @param data      数据
     * @param key       密钥
     * @param cipherAlg 加密算法，如<code>DESede/ECB/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptTriDes(byte[] data, byte[] key, String cipherAlg) throws GeneralSecurityException {
        SecretKey keyObj = createTriDesKey(key);
        return decrypt(data, keyObj, cipherAlg);
    }

    /**
     * 3DES加密
     *
     * @param data      数据
     * @param key       密钥
     * @param iv        向量
     * @param cipherAlg 加密算法，如<code>DESede/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptTriDesWithIV(byte[] data, byte[] key, byte[] iv, String cipherAlg)
            throws GeneralSecurityException {
        SecretKey keyObj = createTriDesKey(key);
        return encrypt(data, keyObj, new IvParameterSpec(iv), cipherAlg);
    }

    /**
     * 3DES解密
     *
     * @param data      数据
     * @param key       密钥
     * @param iv        向量
     * @param cipherAlg 加密算法，如<code>DESede/CBC/PKCS5Padding</code>
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptTriDesWithIV(byte[] data, byte[] key, byte[] iv, String cipherAlg)
            throws GeneralSecurityException {
        SecretKey keyObj = createTriDesKey(key);
        return decrypt(data, keyObj, new IvParameterSpec(iv), cipherAlg);
    }

    /**
     * 3DES加密，使用加密算法<code>DESede/ECB/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptTriDes(byte[] data, byte[] key) throws GeneralSecurityException {
        return encryptTriDes(data, key, DEFAULT_CIPHER_ALG_TRIDES_ECB);
    }

    /**
     * 3DES解密，使用加密算法<code>DESede/ECB/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptTriDes(byte[] data, byte[] key) throws GeneralSecurityException {
        return decryptTriDes(data, key, DEFAULT_CIPHER_ALG_TRIDES_ECB);
    }

    /**
     * 3DES加密，使用加密算法<code>DESede/CBC/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @param iv   向量
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encryptTriDesWithIV(byte[] data, byte[] key, byte[] iv) throws GeneralSecurityException {
        return encryptTriDesWithIV(data, key, iv, DEFAULT_CIPHER_ALG_TRIDES_CBC);
    }

    /**
     * 3DES解密，使用加密算法<code>DESede/CBC/PKCS5Padding</code>
     *
     * @param data 数据
     * @param key  密钥
     * @param iv   向量
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decryptTriDesWithIV(byte[] data, byte[] key, byte[] iv) throws GeneralSecurityException {
        return decryptTriDesWithIV(data, key, iv, DEFAULT_CIPHER_ALG_TRIDES_CBC);
    }

    /**
     * 使用默认的密钥和加密算法进行DES加密
     *
     * @param plainText 原文
     * @return 密文
     */
    public static String encryptDesDefault(String plainText) {
        byte[] encryptBytes = encryptDesDefault(plainText.getBytes(CHARSET));
        if (null != encryptBytes) {
            return Base64.encodeBase64String(encryptBytes);
        }
        return null;
    }

    /**
     * 使用默认的密钥和加密算法进行DES加密
     *
     * @param data 原文
     * @return 密文
     */
    public static byte[] encryptDesDefault(byte[] data) {
        try {
            return encryptDesWithIV(data, CRYPT_KEY_BYTES, CRYPT_IV_BYTES);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 使用默认的密钥和加密算法进行DES解密
     *
     * @param encryptText 密文
     * @return 原文
     */
    public static String decryptDesDefault(String encryptText) {
        byte[] decryptBytes = decryptDesDefault(Base64.decodeBase64(encryptText));
        if (null != decryptBytes) {
            return new String(decryptBytes, CHARSET);
        }
        return null;
    }

    /**
     * 使用默认的密钥和加密算法进行DES解密
     *
     * @param data 密文
     * @return 原文
     */
    public static byte[] decryptDesDefault(byte[] data) {
        try {
            return decryptDesWithIV(data, CRYPT_KEY_BYTES, CRYPT_IV_BYTES);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 使用默认的密钥和加密算法进行3DES加密
     *
     * @param plainText 原文
     * @return 密文
     */
    public static String encryptTriDesDefault(String plainText) {
        byte[] encryptBytes = encryptTriDesDefault(plainText.getBytes(CHARSET));
        if (null != encryptBytes) {
            return Base64.encodeBase64String(encryptBytes);
        }
        return null;
    }

    /**
     * 使用默认的密钥和加密算法进行3DES加密
     *
     * @param data 原文
     * @return 密文
     */
    public static byte[] encryptTriDesDefault(byte[] data) {
        try {
            return encryptTriDesWithIV(data, CRYPT_KEY_BYTES, CRYPT_IV_BYTES);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 使用默认的密钥和加密算法进行3DES解密
     *
     * @param encryptText 密文
     * @return 原文
     */
    public static String decryptTriDesDefault(String encryptText) {
        return decryptTriDesDefault(Base64.decodeBase64(encryptText)).get( decryptBytes -> new String(decryptBytes, CHARSET) );
    }

    /**
     * 使用默认的密钥和加密算法进行3DES解密
     *
     * @param data 密文
     * @return 原文
     */
    public static NcuOptional<byte[]> decryptTriDesDefault(byte[] data) {
        try {
            return NcuOptional.of(decryptTriDesWithIV(data, CRYPT_KEY_BYTES, CRYPT_IV_BYTES));
        } catch (Exception e) {
        }
        return NcuOptional.empty();
    }

    private static byte[] buildTriDesKey(String keyString) {
        byte[] key = new byte[24];
        byte[] keySrcBytes = keyString.getBytes(CHARSET);

        if (key.length > keySrcBytes.length) {
            System.arraycopy(keySrcBytes, 0, key, 0, keySrcBytes.length);
        } else {
            System.arraycopy(keySrcBytes, 0, key, 0, key.length);
        }
        return key;
    }

}
