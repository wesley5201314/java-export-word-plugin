package org.java.export.plugin.api;

import freemarker.template.Configuration;
import org.java.export.plugin.Bean.RichObject;
import org.java.export.plugin.core.Freemarker;
import org.java.export.plugin.core.RichHtmlHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by wesley on 2017-05-10.
 * 对外提供调用的接口
 */
public class WordGeneratorWithFreemarker {

    private static final Logger logger = LoggerFactory.getLogger(WordGeneratorWithFreemarker.class);
    /**
     * 创建doc文件
     * @param templatePath 模板所在路径 xxx/xxx/template
     * @param templateName 模板名字 xxx.ftl
     * @param dataMap 数据集合
     * @param outPath 输出文件路径  xxx/xxx/xxx.doc
     */
    public static void createDoc(String templatePath, String templateName, Map<String, Object> dataMap, String outPath) throws Exception{
        logger.debug("WordGeneratorWithFreemarker createDoc()");
        Freemarker.fprint(templatePath,templateName,dataMap,outPath);
    }

    /**
     * 创建富文本Html处理器，主要处理图片及编码
     * @param richObject 需要的参数
     * @return
     */
    public static RichHtmlHandler createRichHtmlHandler(RichObject richObject) throws Exception{
        logger.debug("WordGeneratorWithFreemarker createRichHtmlHandler()");
        RichHtmlHandler richHtmlHandler = new RichHtmlHandler(richObject);
        return richHtmlHandler;
    }

    /**
     * 获取图片的64位字符串
     * @param richHtmlHandlerList
     * @return
     */
    public static String getImagesBase64String(List<RichHtmlHandler> richHtmlHandlerList){
        logger.debug("WordGeneratorWithFreemarker getImagesBase64String()");
        String imagesBase64String = "";
        for(RichHtmlHandler richHtmlHandler:richHtmlHandlerList){
            if(!CollectionUtils.isEmpty(richHtmlHandler.getDocBase64BlockResults())){
                for (String item : richHtmlHandler.getDocBase64BlockResults()) {
                    imagesBase64String += item + "\n";
                }
            }
        }
        logger.debug("WordGeneratorWithFreemarker getImagesBase64String() result:"+imagesBase64String);
        return imagesBase64String;
    }

    /**
     * 获取图片在xml中的端路径
     * @param richHtmlHandlerList
     * @return
     */
    public static String getXmlImgHref(List<RichHtmlHandler> richHtmlHandlerList){
        logger.debug("WordGeneratorWithFreemarker getXmlImgHref()");
        String xmlImgHref = "";
        for(RichHtmlHandler richHtmlHandler:richHtmlHandlerList){
            if(!CollectionUtils.isEmpty(richHtmlHandler.getXmlImgRefs())){
                for (String item : richHtmlHandler.getXmlImgRefs()) {
                    xmlImgHref += item + "\n";
                }
            }
        }
        logger.debug("WordGeneratorWithFreemarker getXmlImgHref() result:"+xmlImgHref);
        return xmlImgHref;
    }
}
