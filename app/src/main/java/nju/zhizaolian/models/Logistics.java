package nju.zhizaolian.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by lk on 15/4/27.
 */
public class Logistics {
    // Fields

    private Integer orderId;
    private Timestamp inPostSampleClothesTime;
    private String inPostSampleClothesType;
    private String inPostSampleClothesNumber;
    private String sampleClothesType;
    private String sampleClothesAddress;
    private String sampleClothesName;
    private String sampleClothesPhone;
    private Timestamp sampleClothesTime;
    private String sampleClothesRemark;
    private String sampleClothesNumber;
    private String productClothesType;		//快递名称
    private String productClothesAddress;	//收货人地址
    private String productClothesPrice;		//快递价格
    private String productClothesNumber;	//快递单号
    private String productClothesName;      //发货人姓名
    private String productClothesPhone;		//收货人电话
    private Timestamp productClothesTime;   //发货时间
    private String productClothesRemark;    //发货备注

    // Constructors

    /** default constructor */
    public Logistics() {
    }

    /** minimal constructor */
    public Logistics(Integer orderId) {
        this.orderId = orderId;
    }

    /** full constructor */
    public Logistics(Integer orderId, Timestamp inPostSampleClothesTime,
                     String inPostSampleClothesType, String inPostSampleClothesNumber,
                     String sampleClothesType, String sampleClothesAddress,
                     String sampleClothesName, String sampleClothesPhone,
                     Timestamp sampleClothesTime, String sampleClothesRemark,
                     String productClothesType, String productClothesAddress,
                     String productClothesPrice, String productClothesNumber,
                     String productClothesName, String productClothesPhone,
                     Timestamp productClothesTime, String productClothesRemark) {
        this.orderId = orderId;
        this.inPostSampleClothesTime = inPostSampleClothesTime;
        this.inPostSampleClothesType = inPostSampleClothesType;
        this.inPostSampleClothesNumber = inPostSampleClothesNumber;
        this.sampleClothesType = sampleClothesType;
        this.sampleClothesAddress = sampleClothesAddress;
        this.sampleClothesName = sampleClothesName;
        this.sampleClothesPhone = sampleClothesPhone;
        this.sampleClothesTime = sampleClothesTime;
        this.sampleClothesRemark = sampleClothesRemark;
        this.productClothesType = productClothesType;
        this.productClothesAddress = productClothesAddress;
        this.productClothesPrice = productClothesPrice;
        this.productClothesNumber = productClothesNumber;
        this.productClothesName = productClothesName;
        this.productClothesPhone = productClothesPhone;
        this.productClothesTime = productClothesTime;
        this.productClothesRemark = productClothesRemark;
    }
    public static Logistics fromJson(JSONObject jsonObject){
        Logistics logistics=new Logistics();
        try {
            logistics.inPostSampleClothesNumber=jsonObject.getString("inPostSampleClothesNumber");
            logistics.inPostSampleClothesTime= Timestamp.valueOf(jsonObject.getString("inPostSampleClothesTime"));
            logistics.inPostSampleClothesType=jsonObject.getString("inPostSampleClothesType");
            logistics.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            logistics.productClothesAddress=jsonObject.getString("productClothesAddress");
            logistics.productClothesName=jsonObject.getString("productClothesName");
            logistics.productClothesNumber=jsonObject.getString("productClothesNumber");
            logistics.productClothesPhone=jsonObject.getString("productClothesPhone");
            logistics.productClothesPrice=jsonObject.getString("productClothesPrice");
            logistics.productClothesRemark=jsonObject.getString("productClothesRemark");
            logistics.productClothesTime= Timestamp.valueOf(jsonObject.getString("productClothesTime"));
            logistics.productClothesType=jsonObject.getString("productClothesType");
            logistics.sampleClothesAddress=jsonObject.getString("sampleClothesAddress");
            logistics.sampleClothesName=jsonObject.getString("sampleClothesName");
            logistics.sampleClothesNumber=jsonObject.getString("sampleClothesNumber");
            logistics.sampleClothesPhone=jsonObject.getString("sampleClothesPhone");
            logistics.sampleClothesRemark=jsonObject.getString("sampleClothesRemark");
            logistics.sampleClothesTime= Timestamp.valueOf(jsonObject.getString("sampleClothesTime"));
            logistics.sampleClothesType=jsonObject.getString("sampleClothesType");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return logistics;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Timestamp getInPostSampleClothesTime() {
        return inPostSampleClothesTime;
    }

    public void setInPostSampleClothesTime(Timestamp inPostSampleClothesTime) {
        this.inPostSampleClothesTime = inPostSampleClothesTime;
    }

    public String getInPostSampleClothesType() {
        return inPostSampleClothesType;
    }

    public void setInPostSampleClothesType(String inPostSampleClothesType) {
        this.inPostSampleClothesType = inPostSampleClothesType;
    }

    public String getInPostSampleClothesNumber() {
        return inPostSampleClothesNumber;
    }

    public void setInPostSampleClothesNumber(String inPostSampleClothesNumber) {
        this.inPostSampleClothesNumber = inPostSampleClothesNumber;
    }

    public String getSampleClothesType() {
        return sampleClothesType;
    }

    public void setSampleClothesType(String sampleClothesType) {
        this.sampleClothesType = sampleClothesType;
    }

    public String getSampleClothesAddress() {
        return sampleClothesAddress;
    }

    public void setSampleClothesAddress(String sampleClothesAddress) {
        this.sampleClothesAddress = sampleClothesAddress;
    }

    public String getSampleClothesName() {
        return sampleClothesName;
    }

    public void setSampleClothesName(String sampleClothesName) {
        this.sampleClothesName = sampleClothesName;
    }

    public String getSampleClothesPhone() {
        return sampleClothesPhone;
    }

    public void setSampleClothesPhone(String sampleClothesPhone) {
        this.sampleClothesPhone = sampleClothesPhone;
    }

    public Timestamp getSampleClothesTime() {
        return sampleClothesTime;
    }

    public void setSampleClothesTime(Timestamp sampleClothesTime) {
        this.sampleClothesTime = sampleClothesTime;
    }

    public String getSampleClothesRemark() {
        return sampleClothesRemark;
    }

    public void setSampleClothesRemark(String sampleClothesRemark) {
        this.sampleClothesRemark = sampleClothesRemark;
    }

    public String getSampleClothesNumber() {
        return sampleClothesNumber;
    }

    public void setSampleClothesNumber(String sampleClothesNumber) {
        this.sampleClothesNumber = sampleClothesNumber;
    }

    public String getProductClothesType() {
        return productClothesType;
    }

    public void setProductClothesType(String productClothesType) {
        this.productClothesType = productClothesType;
    }

    public String getProductClothesAddress() {
        return productClothesAddress;
    }

    public void setProductClothesAddress(String productClothesAddress) {
        this.productClothesAddress = productClothesAddress;
    }

    public String getProductClothesPrice() {
        return productClothesPrice;
    }

    public void setProductClothesPrice(String productClothesPrice) {
        this.productClothesPrice = productClothesPrice;
    }

    public String getProductClothesNumber() {
        return productClothesNumber;
    }

    public void setProductClothesNumber(String productClothesNumber) {
        this.productClothesNumber = productClothesNumber;
    }

    public String getProductClothesName() {
        return productClothesName;
    }

    public void setProductClothesName(String productClothesName) {
        this.productClothesName = productClothesName;
    }

    public String getProductClothesPhone() {
        return productClothesPhone;
    }

    public void setProductClothesPhone(String productClothesPhone) {
        this.productClothesPhone = productClothesPhone;
    }

    public Timestamp getProductClothesTime() {
        return productClothesTime;
    }

    public void setProductClothesTime(Timestamp productClothesTime) {
        this.productClothesTime = productClothesTime;
    }

    public String getProductClothesRemark() {
        return productClothesRemark;
    }

    public void setProductClothesRemark(String productClothesRemark) {
        this.productClothesRemark = productClothesRemark;
    }
}
