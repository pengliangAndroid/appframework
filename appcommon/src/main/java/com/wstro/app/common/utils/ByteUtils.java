package com.wstro.app.common.utils;

/**
 * Created by pengl on 2016/1/13.
 */
public class ByteUtils {

    //byte 与 int 的相互转换
    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * 将两个16以内的数字合并为高四位低四位
     * @param src0
     * @param src1
     * @return
     */
    public static byte uniteNumber(byte src0, byte src1)
    {
        byte _b0 = src0;
        _b0 = (byte)(_b0 << 4);
        byte _b1 = src1;
        byte ret = (byte)(_b0 ^ _b1);
        return ret;
    }

    /**
     * 合并多个字节数组
     *
     * @param data
     * @return
     */
    public static byte[] uniteBytes(byte[]... data) {
        int length = 0;
        for (byte[] msg : data) {
            length += msg.length;
        }
        byte[] newData = new byte[length];
        int i = 0;
        for (byte[] msg : data) {
            System.arraycopy(msg, 0, newData, i, msg.length);
            i += msg.length;
        }

        return newData;
    }

    /**
     * 解析十六进制字符至16进制字符串
     *
     */
    public static String decodeByteToHexString(byte src) {
        byte[] des = new byte[2];
        des[1] = (byte) (src & 0x0f);
        des[0] = (byte) ((src & 0xf0) >> 4);
        return Integer.toHexString(des[0]) + Integer.toHexString(des[1]);
    }

    public static String decodeBytesToHexString(byte[] data) {
        String result = new String();
        for (byte dd : data) {
            result = result.concat(decodeByteToHexString(dd));
        }
        return result;
    }

    /**
     * 解析十六进制字符
     *
     * @param src
     * @return
     */
    public static byte[] decodeByte(byte src) {
        byte[] des = new byte[2];
        des[0] = (byte) (src & 0x0f);
        des[1] = (byte) ((src & 0xf0) >> 4);
        return des;
    }

    public static byte[] chatOrders(String c) {
        byte[] m = c.getBytes();
        if (m.length % 2 == 0) {
            byte[] bytes = new byte[m.length / 2];
            for (int i = 0, j = 0; i < m.length; i += 2, j++) {
                bytes[j] = uniteByte(m[i], m[i + 1]);
            }
            return bytes;
        }
        return null;
    }

    // 实现两个十六进制字符合并为一个8位大小字节
    public static byte uniteByte(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }


}
