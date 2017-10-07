package com.wstro.app.common.utils.encrpyt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String defaultCharset = "utf-8";
    private static final String HEX = "0123456789ABCDEF";


    public static byte[] hex2Byte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String byte2Hex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            sb.append(HEX.charAt(buf[i] >> 4 & 0xF));
            sb.append(HEX.charAt(buf[i] & 0xF));
        }
        return sb.toString();
    }

    public static byte[] encrypt(byte[] key, byte[] in) {
        try {
            /* AES算法 */
            SecretKey secretKey = new SecretKeySpec(key, "AES");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式 */
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
            byte[] resultBytes = cipher.doFinal(in); // 正式执行加密操作
            return resultBytes;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] key, byte[] in) {
        try {
			/* AES算法 */
            SecretKey secretKey = new SecretKeySpec(key, "AES");// 获得密钥
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
            byte[] resultBytes = cipher.doFinal(in);// 正式执行解密操作
            return resultBytes;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String key, String cleartext) throws Exception {
        byte[] rawKey = key.getBytes(defaultCharset);
        byte[] result = encrypt(rawKey, cleartext.getBytes(defaultCharset));
        return byte2Hex(result);
    }

    public static String decrypt(String key, String encrypted) throws Exception {
        byte[] rawKey = key.getBytes(defaultCharset);
        byte[] enc = hex2Byte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, defaultCharset);
    }

    /**
     * AES 加密
     *
     * @param key 加密的key
     * @param in  加密原始数据
     * @return 加密后数据
     * @throws Exception
     */
    public static byte[] encrypt(String key, byte[] in) throws Exception {
        byte[] rawKey = key.getBytes(defaultCharset);
        byte[] enc = encrypt(rawKey, in);
        return enc;
    }

    /**
     * AES 解密
     *
     * @param key 解密key
     * @param in  要解密数据
     * @return 解密后数据
     * @throws Exception
     */
    public static byte[] decrypt(String key, byte[] in) throws Exception {
        byte[] rawKey = key.getBytes(defaultCharset);
        byte[] result = decrypt(rawKey, in);
        return result;
    }
}
