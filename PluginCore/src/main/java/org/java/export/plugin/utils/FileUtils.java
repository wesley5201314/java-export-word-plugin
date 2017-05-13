package org.java.export.plugin.utils;

/**
 * Created by wesley on 2017-05-10.
 * 文件工具类
 */
public class FileUtils {

    /**
     * 获取文件的名字
     * @param filePath
     * @return
     */
    public static String getFileSuffix(String filePath){
        String str = filePath.substring(0,filePath.lastIndexOf("."));
        return str;
    }

    /**
     * 获取图片类型
     * @param fileTypeName 文件类型名
     * @return
     */
    public static String getImageContentType(String fileTypeName){
        String result="image/jpeg";
        //http://tools.jb51.net/table/http_content_type
        if(("tif").equals(fileTypeName) || ("tiff").equals(fileTypeName)){
            result="image/tiff";
        }else if(("fax").equals(fileTypeName)){
            result="image/fax";
        }else if(("gif").equals(fileTypeName)){
            result="image/gif";
        }else if(("ico").equals(fileTypeName)){
            result="image/x-icon";
        }else if(("jfif").equals(fileTypeName) || ("jpe").equals(fileTypeName)
                ||("jpeg").equals(fileTypeName)  || ("jpg").equals(fileTypeName)){
            result="image/jpeg";
        }else if(("net").equals(fileTypeName)){
            result="image/pnetvue";
        }else if(("png").equals(fileTypeName) || ("bmp").equals(fileTypeName) ){
            result="image/png";
        }else if(("rp").equals(fileTypeName)){
            result="image/vnd.rn-realpix";
        }
        return result;
    }
}
