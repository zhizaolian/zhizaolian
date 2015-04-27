package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/27.
 */
public class FabricCost {
    private Integer id;
    private Integer orderId;
    private String fabricName;
    private Float tearPerMeter;
    private Float price;
    private Float costPerMeter;
    private Float tearPerMeterSampleAmountProduct;
    private Float tearPerMeterAskAmountProduct;
    // Constructors

    /** default constructor */
    public FabricCost() {
    }

    /** full constructor */
    public FabricCost(Integer orderId, String fabricName, Float tearPerMeter,
                      Float price, Float costPerMeter) {
        this.orderId = orderId;
        this.fabricName = fabricName;
        this.tearPerMeter = tearPerMeter;
        this.price = price;
        this.costPerMeter = costPerMeter;
    }
    public static FabricCost fromJson(JSONObject jsonObject){
        FabricCost fabricCost=new FabricCost();
        try{
            fabricCost.costPerMeter= (Float) jsonObject.get("costPerMeter");
            fabricCost.fabricName=jsonObject.getString("fabricName");
            fabricCost.id= Integer.valueOf(jsonObject.getString("id"));
            fabricCost.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            fabricCost.price= Float.valueOf(jsonObject.getString("price"));
            fabricCost.tearPerMeter= Float.valueOf(jsonObject.getString("tearPerMeter"));
            fabricCost.tearPerMeterAskAmountProduct= Float.valueOf(jsonObject.getString("tearPerMeterAskAmountProduct"));
            fabricCost.tearPerMeterSampleAmountProduct= Float.valueOf(jsonObject.getString("tearPerMeterSampleAmountProduct"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return fabricCost;
    }
    public static ArrayList<FabricCost> fromJson(JSONArray jsonArray){
        ArrayList<FabricCost> fabricCostArrayList=new ArrayList<FabricCost>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
           FabricCost fabricCost=FabricCost.fromJson(jsonObject);
            if(fabricCost != null){
                fabricCostArrayList.add(fabricCost);
            }
        }
        return fabricCostArrayList;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getTearPerMeter() {
        return tearPerMeter;
    }

    public void setTearPerMeter(Float tearPerMeter) {
        this.tearPerMeter = tearPerMeter;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getCostPerMeter() {
        return costPerMeter;
    }

    public void setCostPerMeter(Float costPerMeter) {
        this.costPerMeter = costPerMeter;
    }

    public Float getTearPerMeterSampleAmountProduct() {
        return tearPerMeterSampleAmountProduct;
    }

    public void setTearPerMeterSampleAmountProduct(Float tearPerMeterSampleAmountProduct) {
        this.tearPerMeterSampleAmountProduct = tearPerMeterSampleAmountProduct;
    }

    public Float getTearPerMeterAskAmountProduct() {
        return tearPerMeterAskAmountProduct;
    }

    public void setTearPerMeterAskAmountProduct(Float tearPerMeterAskAmountProduct) {
        this.tearPerMeterAskAmountProduct = tearPerMeterAskAmountProduct;
    }
}
