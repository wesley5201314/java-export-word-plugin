package org.java.export.plugin.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wesley on 2017-05-10.
 */
public class WordHtmlGeneratorHelper {

    /**
     * 将字符换成3Dus-asci，十进制Accsii码
     * @param source
     * @return
     */
    public static String string2Ascii(String source){
        if(source==null || source==""){
            return null;
        }
        StringBuilder sb=new StringBuilder();

        char[] c=source.toCharArray();
        for(char item : c){
            String itemascii="";
            if(item>=19968 && item<40623){
                itemascii = itemascii = "&#"+(item & 0xffff)+";";
            }else{
                itemascii = item+"";
            }
            sb.append(itemascii);
        }

        return sb.toString();

    }

    /**
     * 将object的所有属性值转成成3Dus-asci编码值
     * @param toHandleObject
     * @param <T>
     * @return
     */
    public static <T extends Object> T handleObject2Ascii(final T toHandleObject){

        class myFieldsCallBack  implements ReflectionUtils.FieldCallback {

            @Override
            public void doWith(Field f) throws IllegalArgumentException,
                    IllegalAccessException {
                if(f.getType().equals(String.class)){
                    //如果是字符串类型
                    f.setAccessible(true);
                    String oldValue=(String)f.get(toHandleObject);
                    if(!StringUtils.isEmpty(oldValue)){
                        f.set(toHandleObject, string2Ascii(oldValue));
                    }
                    //f.setAccessible(false);
                }
            }
        }

        ReflectionUtils.doWithFields(toHandleObject.getClass(), new myFieldsCallBack());

        return toHandleObject;
    }

    /**
     * list所有属性值转成成3Dus-asci编码值
     * @param toHandleObjects
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> handleObjectList2Ascii(final List<T> toHandleObjects){

        for (T t : toHandleObjects) {
            handleObject2Ascii(t);
        }

        return toHandleObjects;
    }

    /**
     * 处理所有的数据
     * @param dataMap
     */
    public static void handleAllObject(Map<String, Object> dataMap){
        //去处理数据
        for (Map.Entry<String, Object> entry : dataMap.entrySet()){
            Object item=entry.getValue();

            //判断object是否是primitive type
            if(isPrimitiveType(item.getClass())){
                if(item.getClass().equals(String.class)){
                    item=WordHtmlGeneratorHelper.string2Ascii((String)item);
                    entry.setValue(item);
                }
            }else if(isCollection(item.getClass())){
                for (Object itemobject : (Collection)item) {
                    WordHtmlGeneratorHelper.handleObject2Ascii(itemobject);
                }
            }else{
                WordHtmlGeneratorHelper.handleObject2Ascii(item);
            }
        }

    }

    /**
     * 拼接数据
     * @param list
     * @param join
     * @return
     */
    public static String joinList(List<String> list,String join ){
        StringBuilder sb=new StringBuilder();
        for (String t : list) {
            sb.append(t);
            if(!StringUtils.isEmpty(join)){
                sb.append(join);
            }
        }
        return sb.toString();
    }


    /**
     * 判断是否是原始类型
     * @param clazz
     * @return
     */
    private static boolean isPrimitiveType(Class<?> clazz){
        return clazz.isEnum() ||
                CharSequence.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Date.class.isAssignableFrom(clazz);

    }

    /**
     * 判断是否是集合类
     * @param clazz
     * @return
     */
    private static boolean isCollection(Class<?> clazz){
        return Collection.class.isAssignableFrom(clazz);
    }

}
