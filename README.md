#export-word-plugin

## 基本介绍： ##

使用java语言开发，依赖freemarker。该插件支持直接客户端调用，也支持web应用。

WordGeneratorWithFreemarker是插件对外提供的一个工具类，也是用户使用插件必要的一个类。该类对外提供四个方法

- createDoc(String templatePath, String templateName, Map<String, Object> dataMap, String outPath)
-
	  /**
     * 创建doc文件
     * @param templatePath 模板所在路径 xxx/xxx/template
     * @param templateName 模板名字 xxx.ftl
     * @param dataMap 数据集合
     * @param outPath 输出文件路径  xxx/xxx/xxx.doc
     */
- createRichHtmlHandler(RichObject richObject)
-       
		/**
	     * 创建富文本Html处理器，主要处理图片及编码
	     * @param richObject 需要的参数
	     * @return
	     */
- getImagesBase64String(List<RichHtmlHandler> richHtmlHandlerList)
-       
    /**
     * 获取图片的64位字符串
     * @param richHtmlHandlerList
     * @return
     */
- getXmlImgHref(List<RichHtmlHandler> richHtmlHandlerList)
-       
    /**
     * 获取图片在xml中的端路径
     * @param richHtmlHandlerList
     * @return
     */

## 基本原理： ##
由于我们是要用word来解析带图片的富文本（说白了就是解析一段html，当然这段html代码是包含img标签：图片）,so...传统的word模板导出（word另存为xml，在修改后缀为ftl）是行不通的，因为他解析不了html代码（至少我目前没有找到这方便的解决方案，大神勿喷~），这样的话我就要换用一种模板来处理这个模板：word模板另存为mht格式，再修改后缀为ftl。剩下的就是后台操作了，找到你存富文本的字段（html代码）获取里面的img标签，找到图片，并把图片解析为base64字符串，填充到我们只做的模板上就ok了。

## 使用场景： ##
比较小众化，比如公司要生成合同导出，生成电子凭据等。

## 使用步骤： ##

参看ClientExample.java这个类。

1、 获取富文本
> 	    //创建富文本
>         StringBuilder sb = new StringBuilder();
>         sb.append("<div>");
>         sb.append("<img style='height:100px;width:200px;display:block;' src='w:\\2.jpg' />");
>         sb.append("</br><span>wesley 演示 导出富文本！@@#######￥￥%%%%………………&&&**~~~~~~&&&&&&&&、、、、、、、、</span>");
>         sb.append("</br><span>----多图分割线---</span>");
>         sb.append("</br><img style='height:100px;width:200px;display:block;' src='w:\\1.jpg' />");
>         sb.append("</br><span>中国梦，幸福梦！</span>");
>         sb.append("</div>");


2、创建RichHtmlHander（处理富文本）

>         RichObject richObject = new RichObject();
>         richObject.setHtml(sb.toString());
>         //--------------------此处可以spring配置文件配置，也可以直接读取属性文件获取------------------
>         //从mht文件中找
>         richObject.setDocSrcLocationPrex("file:///C:/268D4AA4");
>         richObject.setDocSrcParent("word.files");
>         richObject.setNextPartId("01D2C8DD.BC13AF60");
> 
>         richObject.setShapeidPrex("_x56fe__x7247__x0020");
>         richObject.setTypeid("#_x0000_t75");
>         richObject.setSpidPrex("_x0000_i");
> 
>         richObject.setWebAppliction(false);
> 
>         //-----------------------------------------
> 
>         RichHtmlHandler richHtmlHandler = WordGeneratorWithFreemarker.createRichHtmlHandler(richObject);

3、 生成图片在word中的64位编码

4、 生成图片在word中的短路径

>         List<RichHtmlHandlerrichHtmlHandlerList = new ArrayList<RichHtmlHandler>();
>         richHtmlHandlerList.add(richHtmlHandler);
>         WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList);
>         WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList);

5、利用freemarker导出数据

>         String docFilePath = "w:\\temp_by_wesley.doc";
>         String templatePath = Class.class.getResource("/ftl").getPath();
>         templatePath = java.net.URLDecoder.decode(templatePath,"utf-8");//这里我的路径有空格添加此处理
>         logger.debug("------templatePath-------"+templatePath);
>         WordGeneratorWithFreemarker.createDoc(templatePath,"word.ftl",data,docFilePath);

6、导出效果图：

![](http://i.imgur.com/B7mS3bl.png)
