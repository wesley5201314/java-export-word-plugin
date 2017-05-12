package org.java.export.plugin.utils;

import java.util.UUID;

/**
 * Created by wesley on 2017-05-10.
 * 生成UUID工具类
 */
public class UUIDUtils {

    /**
     * 创建一个32位的UUID
     * @return
     */
    public static String get32UUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
