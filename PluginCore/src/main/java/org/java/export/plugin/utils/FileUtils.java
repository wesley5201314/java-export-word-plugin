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
        if(fileTypeName.equals("tif") || fileTypeName.equals("tiff")){
            result="image/tiff";
        }else if(fileTypeName.equals("fax")){
            result="image/fax";
        }else if(fileTypeName.equals("gif")){
            result="image/gif";
        }else if(fileTypeName.equals("ico")){
            result="image/x-icon";
        }else if(fileTypeName.equals("jfif") || fileTypeName.equals("jpe")
                ||fileTypeName.equals("jpeg")  ||fileTypeName.equals("jpg")){
            result="image/jpeg";
        }else if(fileTypeName.equals("net")){
            result="image/pnetvue";
        }else if(fileTypeName.equals("png") || fileTypeName.equals("bmp") ){
            result="image/png";
        }else if(fileTypeName.equals("rp")){
            result="image/vnd.rn-realpix";
        }else if(fileTypeName.equals("rp")){
            result="image/vnd.rn-realpix";
        }
        return result;
    }
}
