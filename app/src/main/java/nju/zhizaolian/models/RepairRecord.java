package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ColorfulCode on 2015/5/1.
 */
public class RepairRecord {
    private String invalidAmount;
    private String orderId;
    private String qualifiedAmount;
    private String recordId;
    private String repairAmount;
    private String repairSide;
    private String repairTime;

    public static RepairRecord fromJson(JSONObject object){
        RepairRecord repairRecord = new RepairRecord();
        try {
        repairRecord.invalidAmount = object.getString("invalidAmount");
        repairRecord.orderId = object.getString("orderId");
        repairRecord.qualifiedAmount = object.getString("qualifiedAmount");
        repairRecord.recordId = object.getString("recordId");
        repairRecord.repairAmount = object.getString("repairAmount");
        repairRecord.repairSide = object.getString("repairSide");
        repairRecord.repairTime = object.getString("repairTime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repairRecord;
    }

    public static ArrayList<RepairRecord> fromJson(JSONArray array){
        ArrayList<RepairRecord> repairRecords = new ArrayList<>();
        for (int i=0;i<array.length();i++){
            try {
                JSONObject object = array.getJSONObject(i);
                RepairRecord repairRecord = fromJson(object);
                repairRecords.add(repairRecord);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return repairRecords;
    }

    public String getInvalidAmount() {
        return invalidAmount;
    }

    public void setInvalidAmount(String invalidAmount) {
        this.invalidAmount = invalidAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQualifiedAmount() {
        return qualifiedAmount;
    }

    public void setQualifiedAmount(String qualifiedAmount) {
        this.qualifiedAmount = qualifiedAmount;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRepairAmount() {
        return repairAmount;
    }

    public void setRepairAmount(String repairAmount) {
        this.repairAmount = repairAmount;
    }

    public String getRepairSide() {
        return repairSide;
    }

    public void setRepairSide(String repairSide) {
        this.repairSide = repairSide;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }
}
