package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/27.
 */
public class Fabric {

    private Integer id;
    private Integer orderId;
    private String fabricName;
    private String fabricAmount;

    public Fabric() {
    }

    public Fabric(Integer id, Integer orderId, String fabricName, String fabricAmount) {
        this.id = id;
        this.orderId = orderId;
        this.fabricName = fabricName;
        this.fabricAmount = fabricAmount;
    }

    public static Fabric fromJson(JSONObject jsonObject){
        Fabric fabric =new Fabric();
        try {
            fabric.fabricAmount=jsonObject.getString("fabricAmount");
            fabric.fabricName=jsonObject.getString("fabricName");
            fabric.id= Integer.valueOf(jsonObject.getString("id"));
            fabric.orderId= Integer.valueOf(jsonObject.getString("orderId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fabric;
    }
    public static ArrayList<Fabric> fromJson(JSONArray jsonArray){
        ArrayList<Fabric> fabricArrayList=new ArrayList<Fabric>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Fabric fabric=Fabric.fromJson(jsonObject);
            if(fabric != null){
                fabricArrayList.add(fabric);
            }
        }
        return fabricArrayList;
    }

    public String getFabricAmount() {
        return fabricAmount;
    }

    public void setFabricAmount(String fabricAmount) {
        this.fabricAmount = fabricAmount;
    }

    public String getFabricName() {
        return fabricName;
    }

    public void setFabricName(String fabricName) {
        this.fabricName = fabricName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
