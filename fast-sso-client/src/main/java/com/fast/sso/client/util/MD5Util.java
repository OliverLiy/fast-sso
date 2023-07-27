package com.fast.sso.client.util;

import java.security.MessageDigest;

/**
 * @author by:ly
 * @ClassName: MD5Utils
 * @Description:  md5工具类
 * @Date: 2021/7/7 15:07
 **/
public class MD5Util {
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 生成32位大写MD5
     * @param SourceString
     * @return
     * @throws Exception
     */
    public static String get32BitMD5(String SourceString) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(SourceString.getBytes());
        byte[] messageDigest = digest.digest();
        return toHexString(messageDigest);
    }

    /**
     * 生成16位大写MD5
     * @param SourceString
     * @return
     * @throws Exception
     */
    public static String get16BitMD5(String SourceString) throws Exception {
        return get32BitMD5(SourceString).substring(8, 24);
    }
}
