package com.wstro.app.common.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created by pengl on 2016/1/13.
 */
public class ByteUtils {

    /**
     * 二位数的格式化，若只有一位，前面添个0
     * @param value
     * @return
     */
    public static String formatOct(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return Integer.toString(value);
    }


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

    public static void releaseImageResource(ImageView circleImageView) {
        if (circleImageView != null && circleImageView.getDrawable() != null) {
            Bitmap oldBitmap = ((BitmapDrawable) circleImageView.getDrawable())
                    .getBitmap();
            circleImageView.setImageDrawable(null);
            if (oldBitmap != null) {
                oldBitmap.recycle();
                oldBitmap = null;
            }
        }
    }

   /* *//**
     * 根据USB通信协议得到字节数组
     * @return
     *//*
    public static byte[] getConfigBytes(){
        byte[] data = new byte[9];
        byte byte0 = (byte) 0;

        // 模式置1处理
        switch (MachineStatus.sModeType) {
            case 1:
                byte0 = (byte) 0;
                break;
            case 2:
                byte0 = (byte) 3;
                break;
            case 3:
                byte0 = (byte) 5;
                break;
            case 4:
                byte0 = (byte) 6;
                break;
            case 5:
                byte0 = (byte) 2;
                break;
            case 6:
                byte0 = (byte) 1;
                break;
        }

        if (MachineStatus.sAnionOn) {
            byte0 = (byte) ((byte0) ^ (1 << 3));
        }

        if (MachineStatus.sForceHeat) {
            byte0 = (byte) ((byte0) ^ (1 << 4));
        }

        if (MachineStatus.sHeatOn) {
            byte0 = (byte) ((byte0) ^ (1 << 5));
        }

        data[0] = byte0;
        // 排风档位
        byte byte1 = (byte) MachineStatus.sExhaustGear;
        // 新风
        byte byte2 = (byte) MachineStatus.sFreshGear;

        data[1] = uniteNumber(byte2, byte1);

        // 定时设置
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        long time = System.currentTimeMillis();
        t.set(MachineStatus.sSystemTimeDiff + time); // 取得系统时间
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;

        data[2] = (byte) hour;
        data[3] = (byte) minute;
        data[4] = (byte) second;
        if (MachineStatus.sTimeOn) {
            data[7] = (byte) (MachineStatus.sTimeOnHour + 128);
        } else {
            data[7] = (byte) MachineStatus.sTimeOnHour;
        }
        data[8] = (byte) MachineStatus.sTimeOnMinute;
        if (MachineStatus.sTimeOff) {
            data[5] = (byte) (MachineStatus.sTimeOffHour + 128);
        } else {
            data[5] = (byte) MachineStatus.sTimeOffHour;
        }
        data[6] = (byte) MachineStatus.sTimeOffMinute;

        byte[] content = uniteBytes(new byte[]{Constants.ORDER_HEAD,

                Constants.ORDER_SETTING, 9 }, data);

        for (int m = 0, n = 0; m < 9; m++) {
            n += data[m];
            if (n > 255) {
                n = n - 255;
            }

            if (m == 8) {
                content = uniteBytes(content, new byte[] { (byte) n });
            }
        }

        content = uniteBytes(content,
                new byte[] { Constants.ORDER_FOOTER });
        return content;
    }
*/

}
