package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/29.
 */
public class DeliveryRecord {
    private Integer recordId;
    private Integer orderId;
    private String sendType;// 发货类型，sample表示样衣，product表示大货
    private String recipientName;// 收件人姓名
    private String recipientAddr;// 收件人地址
    private String recipientPhone;// 收件人手机
    private String senderName;// 发货人姓名
    private String expressName;// 快递名称
    private String expressNumber;// 快递单号
    private String expressPrice;// 快递价格
    private String sendTime;// 发货时间
    private String remark;// 发货备注

    public DeliveryRecord() {
    }

    public static DeliveryRecord fromJson(JSONObject jsonObject){
        DeliveryRecord deliveryRecord=new DeliveryRecord();
        try {
            deliveryRecord.recordId= Integer.valueOf(jsonObject.getString("recordId"));
            deliveryRecord.expressName=jsonObject.getString("expressName");
            deliveryRecord.expressNumber=jsonObject.getString("expressNumber");
            deliveryRecord.expressPrice=jsonObject.getString("expressPrice");
            deliveryRecord.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            deliveryRecord.recipientAddr=jsonObject.getString("recipientAddr");
            deliveryRecord.recipientName=jsonObject.getString("recipientName");
            deliveryRecord.recipientPhone=jsonObject.getString("recipientPhone");
            deliveryRecord.remark=jsonObject.getString("remark");
            deliveryRecord.sendTime=jsonObject.getString("sendTime");
            deliveryRecord.sendType=jsonObject.getString("sendType");
            deliveryRecord.senderName=jsonObject.getString("senderName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deliveryRecord;
    }
    public static ArrayList<DeliveryRecord> fromJson(JSONArray jsonArray){
        ArrayList<DeliveryRecord> deliveryRecordArrayList=new ArrayList<DeliveryRecord>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            DeliveryRecord deliveryRecord=DeliveryRecord.fromJson(jsonObject);
            if(deliveryRecord != null){
                deliveryRecordArrayList.add(deliveryRecord);
            }
        }
        return deliveryRecordArrayList;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAddr() {
        return recipientAddr;
    }

    public void setRecipientAddr(String recipientAddr) {
        this.recipientAddr = recipientAddr;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
