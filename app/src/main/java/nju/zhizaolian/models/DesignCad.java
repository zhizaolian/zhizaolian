package nju.zhizaolian.models;

import org.json.JSONObject;

/**
 * Created by lk on 15/4/27.
 */
public class DesignCad {

    private Integer cadId;
    private Integer orderId;
    private String cadUrl;
    private Short cadVersion;
    private String uploadTime;
    private String cadFabric; //面料
    private String cadPackage; //装箱
    private String cadVersionData; //版型
    private String cadBox; //包装
    private String cadTech; //工艺
    private String cadOther; //其他
    private String cadSide;//制版人姓名
    private String completeTime;//制版完成时间

    /** default constructor */
    public DesignCad() {
    }

    /** full constructor */
    public DesignCad(Integer orderId, String cadUrl, Short cadVersion,
                     String uploadTime) {
        this.orderId = orderId;
        this.cadUrl = cadUrl;
        this.cadVersion = cadVersion;
        this.uploadTime = uploadTime;
    }
    public static DesignCad fromJson(JSONObject jsonObject){
        DesignCad designCad=new DesignCad();
        try{
            designCad.cadBox=jsonObject.getString("cadBox");
            designCad.cadFabric=jsonObject.getString("cadFabric");
            designCad.cadId= Integer.valueOf(jsonObject.getString("cadId"));
            designCad.cadOther=jsonObject.getString("cadOther");
            designCad.cadPackage=jsonObject.getString("cadPackage");
            designCad.cadSide=jsonObject.getString("cadSide");
            designCad.cadTech=jsonObject.getString("cadTech");
            designCad.cadUrl=jsonObject.getString("cadUrl");
            designCad.cadVersion= Short.valueOf(jsonObject.getString("cadVersion"));
            designCad.cadVersionData=jsonObject.getString("cadVersionData");
            designCad.completeTime= jsonObject.getString("completeTime");
            designCad.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            designCad.uploadTime= jsonObject.getString("uploadTime");

        }catch (Exception e){
            e.printStackTrace();

        }
        return designCad;
    }


    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getCadSide() {
        return cadSide;
    }

    public void setCadSide(String cadSide) {
        this.cadSide = cadSide;
    }

    public String getCadBox() {
        return cadBox;
    }

    public void setCadBox(String cadBox) {
        this.cadBox = cadBox;
    }

    public String getCadOther() {
        return cadOther;
    }

    public void setCadOther(String cadOther) {
        this.cadOther = cadOther;
    }

    public String getCadTech() {
        return cadTech;
    }

    public void setCadTech(String cadTech) {
        this.cadTech = cadTech;
    }

    public String getCadVersionData() {
        return cadVersionData;
    }

    public void setCadVersionData(String cadVersionData) {
        this.cadVersionData = cadVersionData;
    }

    public String getCadPackage() {
        return cadPackage;
    }

    public void setCadPackage(String cadPackage) {
        this.cadPackage = cadPackage;
    }

    public String getCadFabric() {
        return cadFabric;
    }

    public void setCadFabric(String cadFabric) {
        this.cadFabric = cadFabric;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Short getCadVersion() {
        return cadVersion;
    }

    public void setCadVersion(Short cadVersion) {
        this.cadVersion = cadVersion;
    }

    public String getCadUrl() {
        return cadUrl;
    }

    public void setCadUrl(String cadUrl) {
        this.cadUrl = cadUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCadId() {
        return cadId;
    }

    public void setCadId(Integer cadId) {
        this.cadId = cadId;
    }
}
