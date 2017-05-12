package org.java.export.plugin.Bean;

/**
 * Created by wesley on 2017-05-10.
 * 封装一些mht参数
 */
public class RichObject {

    private String html; //富文本html文件

    //指doc转成mht之后的一些基础配置
    private String docSrcParent = ""; //文档父类
    private String docSrcLocationPrex = ""; //文档本地前缀
    private String nextPartId; //下一个父类
    private String shapeidPrex; //文档中shapeid前缀
    private String spidPrex; //文档中spid的前缀
    private String typeid; //类型ID

    private Boolean isWebAppliction; //是不是web应用

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDocSrcParent() {
        return docSrcParent;
    }

    public void setDocSrcParent(String docSrcParent) {
        this.docSrcParent = docSrcParent;
    }

    public String getDocSrcLocationPrex() {
        return docSrcLocationPrex;
    }

    public void setDocSrcLocationPrex(String docSrcLocationPrex) {
        this.docSrcLocationPrex = docSrcLocationPrex;
    }

    public String getNextPartId() {
        return nextPartId;
    }

    public void setNextPartId(String nextPartId) {
        this.nextPartId = nextPartId;
    }

    public String getShapeidPrex() {
        return shapeidPrex;
    }

    public void setShapeidPrex(String shapeidPrex) {
        this.shapeidPrex = shapeidPrex;
    }

    public String getSpidPrex() {
        return spidPrex;
    }

    public void setSpidPrex(String spidPrex) {
        this.spidPrex = spidPrex;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public Boolean getWebAppliction() {
        return isWebAppliction;
    }

    public void setWebAppliction(Boolean webAppliction) {
        isWebAppliction = webAppliction;
    }

    @Override
    public String toString() {
        return "RichObject{" +
                "html='" + html + '\'' +
                ", docSrcParent='" + docSrcParent + '\'' +
                ", docSrcLocationPrex='" + docSrcLocationPrex + '\'' +
                ", nextPartId='" + nextPartId + '\'' +
                ", shapeidPrex='" + shapeidPrex + '\'' +
                ", spidPrex='" + spidPrex + '\'' +
                ", typeid='" + typeid + '\'' +
                ", isWebAppliction=" + isWebAppliction +
                '}';
    }
}
