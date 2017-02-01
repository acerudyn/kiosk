package com.util;

/**
 * Created by ade.irwansiah on 11/23/16.
 */
public class Util {
    public static byte[] hex2byte(byte[] b, int offset, int len)
    {
        byte[] d = new byte[len];
        for (int i = 0; i < len * 2; i++)
        {
            int shift = i%2 == 1 ? 0 : 4;
            d[i>>1] |= Character.digit((char) b[offset + i], 16) << shift;
        }
        return d;
    }

    public static byte[] hex2byte(String s)
    {
        return hex2byte(s.getBytes(), 0, s.length() >> 1);
    }

    public static String hexString(byte[] b)
    {
        StringBuffer d = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++)
        {
            char hi = Character.forDigit(b[i] >> 4 & 0x0F, 16);
            char lo = Character.forDigit(b[i] & 0x0F, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }

    public static String padleft(String s, int len, char c)
            throws Exception
    {
        s = s.trim();
        if (s.length() > len) {
            throw new Exception("invalid len " + s.length() + "/" + len);
        }
        StringBuffer d = new StringBuffer(len);
        int fill = len - s.length();
        while (fill-- > 0) {
            d.append(c);
        }
        d.append(s);
        return d.toString();
    }

    public static String strpad(String s, int len)
    {
        StringBuffer d = new StringBuffer(s);
        while (d.length() < len) {
            d.append(' ');
        }
        return d.toString();
    }

    public static String zeropadRight(String s, int len)
    {
        StringBuffer d = new StringBuffer(s);
        while (d.length() < len) {
            d.append('0');
        }
        return d.toString();
    }

    public static String zeropad(String s, int len)
            throws Exception
    {
        return padleft(s, len, '0');
    }

    public static byte[] concat(byte[] array1, byte[] array2)
    {
        byte[] concatArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, concatArray, 0, array1.length);
        System.arraycopy(array2, 0, concatArray, array1.length, array2.length);
        return concatArray;
    }
}