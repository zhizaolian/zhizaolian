package nju.zhizaolian.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lk on 15/4/27.
 */
public class Craft implements Serializable {
    private Integer craftId;
    private Integer orderId;

    private Short needCraft;//0 not need craft 1 need craft
    private float stampDutyMoney =  0;//印花费
    private float washHangDyeMoney =  0;//水洗吊染费
    private float laserMoney =  0;//激光费
    private float embroideryMoney =  0;//刺绣费
    private float crumpleMoney =  0;//压皱费
    private float openVersionMoney =  0;//开版费
    private String craftFileUrl;//工艺文件位置链接
    private String orderSampleStatus;//订单工艺状态
    private String craftLeader;//工艺负责人
    private Timestamp completeTime;//工艺完成时间
    private String crafsManName;//大货工艺负责人
    private Timestamp crafsProduceDate;//大货工艺完成时间

    public Craft(Integer orderId, Integer craftId, Short needCraft, float stampDutyMoney, float washHangDyeMoney, float laserMoney, float embroideryMoney, float crumpleMoney, float openVersionMoney, String craftFileUrl, String orderSampleStatus, String craftLeader, Timestamp completeTime, String crafsManName, Timestamp crafsProduceDate) {
        this.orderId = orderId;
        this.craftId = craftId;
        this.needCraft = needCraft;
        this.stampDutyMoney = stampDutyMoney;
        this.washHangDyeMoney = washHangDyeMoney;
        this.laserMoney = laserMoney;
        this.embroideryMoney = embroideryMoney;
        this.crumpleMoney = crumpleMoney;
        this.openVersionMoney = openVersionMoney;
        this.craftFileUrl = craftFileUrl;
        this.orderSampleStatus = orderSampleStatus;
        this.craftLeader = craftLeader;
        this.completeTime = completeTime;
        this.crafsManName = crafsManName;
        this.crafsProduceDate = crafsProduceDate;
    }

    public Craft() {
    }
    public static Craft fromJson(JSONObject jsonObject){
        Craft craft=new Craft();
        try{
            craft.completeTime= Timestamp.valueOf(jsonObject.getString("completeTime"));
            craft.crafsManName=jsonObject.getString("crafsManName");
            craft.crafsProduceDate= Timestamp.valueOf(jsonObject.getString("crafsProduceDate"));
            craft.craftFileUrl=jsonObject.getString("craftFileUrl");
            craft.craftId= Integer.valueOf(jsonObject.getString("craftId"));
            craft.craftLeader=jsonObject.getString("craftLeader");
            craft.crumpleMoney= Float.parseFloat(jsonObject.getString("crumpleMoney"));
            craft.embroideryMoney= Float.parseFloat(jsonObject.getString("embroideryMoney"));
            craft.laserMoney= Float.parseFloat(jsonObject.getString("laserMoney"));
            craft.needCraft= Short.valueOf(jsonObject.getString("needCraft"));
            craft.openVersionMoney= Float.parseFloat(jsonObject.getString("openVersionMoney"));
            craft.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            craft.orderSampleStatus=jsonObject.getString("orderSampleStatus");
            craft.stampDutyMoney= Float.parseFloat(jsonObject.getString("stampDutyMoney"));
            craft.washHangDyeMoney= Float.parseFloat(jsonObject.getString("washHangDyeMoney"));


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return craft;
    }
    public Integer getCraftId() {
        return craftId;
    }

    public void setCraftId(Integer craftId) {
        this.craftId = craftId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Short getNeedCraft() {
        return needCraft;
    }

    public void setNeedCraft(Short needCraft) {
        this.needCraft = needCraft;
    }

    public float getStampDutyMoney() {
        return stampDutyMoney;
    }

    public void setStampDutyMoney(float stampDutyMoney) {
        this.stampDutyMoney = stampDutyMoney;
    }

    public float getWashHangDyeMoney() {
        return washHangDyeMoney;
    }

    public void setWashHangDyeMoney(float washHangDyeMoney) {
        this.washHangDyeMoney = washHangDyeMoney;
    }

    public float getLaserMoney() {
        return laserMoney;
    }

    public void setLaserMoney(float laserMoney) {
        this.laserMoney = laserMoney;
    }

    public float getEmbroideryMoney() {
        return embroideryMoney;
    }

    public void setEmbroideryMoney(float embroideryMoney) {
        this.embroideryMoney = embroideryMoney;
    }

    public float getCrumpleMoney() {
        return crumpleMoney;
    }

    public void setCrumpleMoney(float crumpleMoney) {
        this.crumpleMoney = crumpleMoney;
    }

    public float getOpenVersionMoney() {
        return openVersionMoney;
    }

    public void setOpenVersionMoney(float openVersionMoney) {
        this.openVersionMoney = openVersionMoney;
    }

    public String getCraftFileUrl() {
        return craftFileUrl;
    }

    public void setCraftFileUrl(String craftFileUrl) {
        this.craftFileUrl = craftFileUrl;
    }

    public String getOrderSampleStatus() {
        return orderSampleStatus;
    }

    public void setOrderSampleStatus(String orderSampleStatus) {
        this.orderSampleStatus = orderSampleStatus;
    }

    public String getCraftLeader() {
        return craftLeader;
    }

    public void setCraftLeader(String craftLeader) {
        this.craftLeader = craftLeader;
    }

    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    public String getCrafsManName() {
        return crafsManName;
    }

    public void setCrafsManName(String crafsManName) {
        this.crafsManName = crafsManName;
    }

    public Timestamp getCrafsProduceDate() {
        return crafsProduceDate;
    }

    public void setCrafsProduceDate(Timestamp crafsProduceDate) {
        this.crafsProduceDate = crafsProduceDate;
    }
}
