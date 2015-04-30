package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ColorfulCode on 2015/5/1.
 */
public class SweaterOperateRecord {
    private String operateRemark;
    private String operatePerson;
    private String operateTime;
    private String operateId;
    private String taskName;
    private String orderId;

    public static SweaterOperateRecord fromJson(JSONObject object){
        SweaterOperateRecord sweaterOperateRecord = new SweaterOperateRecord();
        try {
            sweaterOperateRecord.operateRemark = object.getString("operateRemark");
            sweaterOperateRecord.operatePerson = object.getString("operatePerson");
            sweaterOperateRecord.operateTime = object.getString("operateTime");
            sweaterOperateRecord.operateId = object.getString("operateId");
            sweaterOperateRecord.taskName = object.getString("taskName");
            sweaterOperateRecord.orderId = object.getString("orderId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sweaterOperateRecord;
    }

    public static ArrayList<SweaterOperateRecord> fromJson(JSONArray array){
        ArrayList<SweaterOperateRecord> records = new ArrayList<>();
        for(int i=0;i<array.length();i++){
            try {
                JSONObject object = array.getJSONObject(i);
                SweaterOperateRecord sweaterOperateRecord = fromJson(object);
                records.add(sweaterOperateRecord);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return  records;
    }

    public String getOperateRemark() {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark) {
        this.operateRemark = operateRemark;
    }

    public String getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
