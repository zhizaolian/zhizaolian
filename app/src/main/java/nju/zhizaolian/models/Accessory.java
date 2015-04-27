package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/27.
 */
public class Accessory {

    private Integer id;
    private Integer orderId;
    private String accessoryName;
    private String accessoryQuery;

    public Accessory() {
    }

    public Accessory(Integer id, Integer orderId, String accessoryQuery, String accessoryName) {
        this.id = id;
        this.orderId = orderId;
        this.accessoryQuery = accessoryQuery;
        this.accessoryName = accessoryName;
    }
    public static Accessory fromJson(JSONObject jsonObject){
        Accessory accessory=new Accessory();
        try{
            accessory.id= Integer.valueOf(jsonObject.getString("id"));
            accessory.accessoryName=jsonObject.getString("accessoryName");
            accessory.accessoryQuery=jsonObject.getString("accessoryQuery");
            accessory.orderId= Integer.valueOf(jsonObject.getString("orderId"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return accessory;
    }
    public static ArrayList<Accessory> fromJson(JSONArray jsonArray){
        ArrayList<Accessory> accessoryArrayList=new ArrayList<Accessory>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Accessory accessory=Accessory.fromJson(jsonObject);
            if(accessory != null){
                accessoryArrayList.add(accessory);
            }
        }
        return  accessoryArrayList;


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

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public String getAccessoryQuery() {
        return accessoryQuery;
    }

    public void setAccessoryQuery(String accessoryQuery) {
        this.accessoryQuery = accessoryQuery;
    }
}
