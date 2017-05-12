package org.java.export.plugin.core;

import org.apache.commons.lang3.StringUtils;
import org.java.export.plugin.Bean.RichObject;
import org.java.export.plugin.utils.FileUtils;
import org.java.export.plugin.utils.RequestResponseContext;
import org.java.export.plugin.utils.UUIDUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2017-05-10.
 * 富文本Html处理器
 */
public class RichHtmlHandler {

    private static final Logger logger = LoggerFactory.getLogger(RichHtmlHandler.class);

    private String handledDocBodyBlock; //富文本所在doc中的区域
    private List<String> docBase64BlockResults = new ArrayList<String>(); //文档中图片64位编码的结果区
    private List<String> xmlImgRefs = new ArrayList<String>(); //图片在xml中的短路径
    private RichObject richObject = null; //富文本对象
    private Document doc = null; //默认文档为空

    public RichHtmlHandler(){
        super();
    }

    public RichHtmlHandler(RichObject richObj) throws IOException {
            this.richObject = richObj;
            doc = Jsoup.parse(wrappHtml(richObject.getHtml()));
            handledHtml(richObject.getWebAppliction());
    }

    /**
     * 处理html
     * @param isWebApplication
     * @throws IOException
     */
    public void handledHtml(boolean isWebApplication)
            throws IOException {
        logger.debug("RichHtmlHandler handledHtml isWebApplication:"+isWebApplication);
        Elements imags = doc.getElementsByTag("img");

        if (imags == null || imags.size() == 0) {
            // 返回编码后字符串
            return;
            //handledDocBodyBlock = WordHtmlGeneratorHelper.string2Ascii(html);
        }

        // 转换成word mht 能识别图片标签内容，去替换html中的图片标签
        for (Element item : imags) {
            // 把文件取出来
            String src = item.attr("src");
            String srcRealPath = src;

            if (isWebApplication) {
                String contentPath= RequestResponseContext.getRequest().getContextPath();
                if(!StringUtils.isEmpty(contentPath)){
                    if(src.startsWith(contentPath)){
                        src=src.substring(contentPath.length());
                    }
                }

                srcRealPath = RequestResponseContext.getRequest().getSession()
                        .getServletContext().getRealPath(src);

            }

            File imageFile = new File(srcRealPath);
            String imageFielShortName = imageFile.getName();
            String fileTypeName = FileUtils.getFileSuffix(srcRealPath);

            String docFileName = "image" + UUIDUtils.get32UUID() + "."
                    + fileTypeName;

            String srcLocationShortName = richObject.getDocSrcParent() + "/" + docFileName;

            String styleAttr = item.attr("style"); // 样式
            //高度
            String imagHeightStr=item.attr("height");;
            if(StringUtils.isEmpty(imagHeightStr)){
                imagHeightStr = getStyleAttrValue(styleAttr, "height");
            }
            //宽度
            String imagWidthStr=item.attr("width");;
            if(StringUtils.isEmpty(imagHeightStr)){
                imagHeightStr = getStyleAttrValue(styleAttr, "width");
            }

            imagHeightStr = imagHeightStr.replace("px", "");
            imagWidthStr = imagWidthStr.replace("px", "");
            if(StringUtils.isEmpty(imagHeightStr)){
                //去得到默认的文件高度
                imagHeightStr="0";
            }
            if(StringUtils.isEmpty(imagWidthStr)){
                imagWidthStr="0";
            }
            int imageHeight = Integer.parseInt(imagHeightStr);
            int imageWidth = Integer.parseInt(imagWidthStr);

            //得到文件的word mht的body块
            String handledDocBodyBlock = WordImageConvertor.toDocBodyBlock(srcRealPath,
                    imageFielShortName, imageHeight, imageWidth,styleAttr,
                    srcLocationShortName, richObject.getShapeidPrex(), richObject.getSpidPrex(), richObject.getTypeid());
            item.after(handledDocBodyBlock);
            //item.parent().append(handledDocBodyBlock);
            item.remove();
            // 去替换原生的html中的imag
            String base64Content = WordImageConvertor
                    .imageToBase64(srcRealPath);
            String contextLocation = richObject.getDocSrcLocationPrex() + "/" + richObject.getDocSrcParent()
                    + "/" + docFileName;

            String docBase64BlockResult = WordImageConvertor
                    .generateImageBase64Block(richObject.getNextPartId(), contextLocation,
                            fileTypeName, base64Content);
            docBase64BlockResults.add(docBase64BlockResult);

            String imgXMLHref = "<o:File HRef=3D\"" + docFileName + "\"/>";
            xmlImgRefs.add(imgXMLHref);

        }

    }

    /**
     * 获取样式的属性值
     * @param style
     * @param attributeKey
     * @return
     */
    private String getStyleAttrValue(String style, String attributeKey) {
        if (StringUtils.isEmpty(style)) {
            return "";
        }
        // 以";"分割
        String[] styleAttrValues = style.split(";");
        for (String item : styleAttrValues) {
            // 在以 ":"分割
            String[] keyValuePairs = item.split(":");
            if (attributeKey.equals(keyValuePairs[0])) {
                return keyValuePairs[1];
            }
        }
        return "";
    }

    /**
     * 给html添加必须的标签
     * @param html
     * @return
     */
    private String wrappHtml(String html){
        // 因为传递过来都是不完整,需要添加以下标签
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body>");
        sb.append(html);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    public String getHandledDocBodyBlock() {
        String raw = WordHtmlGeneratorHelper.string2Ascii(doc.getElementsByTag("body").html());
        return raw.replace("=3D", "=").replace("=", "=3D");
        //return handledDocBodyBlock;
    }

    public void setHandledDocBodyBlock(String handledDocBodyBlock) {
        this.handledDocBodyBlock = handledDocBodyBlock;
    }

    public List<String> getDocBase64BlockResults() {
        return docBase64BlockResults;
    }

    public void setDocBase64BlockResults(List<String> docBase64BlockResults) {
        this.docBase64BlockResults = docBase64BlockResults;
    }

    public List<String> getXmlImgRefs() {
        return xmlImgRefs;
    }

    public void setXmlImgRefs(List<String> xmlImgRefs) {
        this.xmlImgRefs = xmlImgRefs;
    }

    public RichObject getRichObject() {
        return richObject;
    }

    public void setRichObject(RichObject richObject) {
        this.richObject = richObject;
    }
}
