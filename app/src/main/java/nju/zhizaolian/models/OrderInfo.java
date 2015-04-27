package nju.zhizaolian.models;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/27.
 */
public class OrderInfo {
    private String taskId;
    private Craft craft;
    private ArrayList<Accessory> accessories;
    private Custom custom;
    private ArrayList<FabricCost> fabricCosts;
    private ArrayList<VersionData> versionDatas;
    private String processInstanceId;
    private Order order;
    private ArrayList<AccessoryCost> accessoryCosts;
    private String orderSampleAmount;
    private Quote quote;
    private ArrayList<Fabric> fabrics;
    private Employee employee=null;
    private Logistics logistics;
    private String orderId;
    public OrderInfo() {
    }

    public static OrderInfo fromJson(JSONObject jsonObject){
        OrderInfo orderInfo=new OrderInfo();
        try{
            orderInfo.taskId= String.valueOf(jsonObject.get("taskId"));
            orderInfo.craft= Craft.fromJson(jsonObject.getJSONObject("craft"));
            orderInfo.accessories=Accessory.fromJson(jsonObject.getJSONArray("accessorys"));
            orderInfo.custom=Custom.fromJson(jsonObject.getJSONObject("customer"));
            orderInfo.fabricCosts=FabricCost.fromJson(jsonObject.getJSONArray("fabricCosts"));
            orderInfo.versionDatas=VersionData.fromJson(jsonObject.getJSONArray("versions"));
            orderInfo.processInstanceId=jsonObject.getString("processInstanceId");
            orderInfo.order=Order.fromJson(jsonObject.getJSONObject("order"));
            orderInfo.accessoryCosts=AccessoryCost.fromJson(jsonObject.getJSONArray("accessoryCosts"));
            orderInfo.orderSampleAmount=jsonObject.getString("orderSampleAmount");
            orderInfo.quote= Quote.fromJson(jsonObject.getJSONObject("quote"));
            orderInfo.fabrics=Fabric.fromJson(jsonObject.getJSONArray("fabrics"));
            orderInfo.employee=Employee.fromJson(jsonObject.getJSONObject("employee"));
            orderInfo.logistics=Logistics.fromJson(jsonObject.getJSONObject("logistics"));
            orderInfo.orderId=jsonObject.getString("orderId");

        }catch (Exception e){
            e.printStackTrace();

        }



        return orderInfo;
    }





}
