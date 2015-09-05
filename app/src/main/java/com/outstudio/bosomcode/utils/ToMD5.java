package com.outstudio.bosomcode.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 将字符串转化为MD5值
 * Created by mima123 on 15/8/4.
 */
public class ToMD5 {
    public static final String DIGEST_TYPE = "MD5";

    public static String getMD5(String text) {
        StringBuffer buf = null;
        try {
            // 生成实现指定摘要算法的MessageDigest对象
            MessageDigest md = MessageDigest.getInstance(DIGEST_TYPE);
            // 使用指定的字节数组更新摘要
            md.update(text.getBytes());
            // 通过执行诸如填充之类的最终计算完成哈希计算
            byte[] b = md.digest();
            // 生成具体的MD5密码到数组
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buf.toString();

    }
}
