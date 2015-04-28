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
            //orderInfo.employee=Employee.fromJson(jsonObject.getJSONObject("employee"));
            orderInfo.logistics=Logistics.fromJson(jsonObject.getJSONObject("logistics"));
            orderInfo.orderId=jsonObject.getString("orderId");

        }catch (Exception e){
            e.printStackTrace();

        }



        return orderInfo;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Craft getCraft() {
        return craft;
    }

    public void setCraft(Craft craft) {
        this.craft = craft;
    }

    public ArrayList<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(ArrayList<Accessory> accessories) {
        this.accessories = accessories;
    }

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public ArrayList<FabricCost> getFabricCosts() {
        return fabricCosts;
    }

    public void setFabricCosts(ArrayList<FabricCost> fabricCosts) {
        this.fabricCosts = fabricCosts;
    }

    public ArrayList<VersionData> getVersionDatas() {
        return versionDatas;
    }

    public void setVersionDatas(ArrayList<VersionData> versionDatas) {
        this.versionDatas = versionDatas;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<AccessoryCost> getAccessoryCosts() {
        return accessoryCosts;
    }

    public void setAccessoryCosts(ArrayList<AccessoryCost> accessoryCosts) {
        this.accessoryCosts = accessoryCosts;
    }

    public String getOrderSampleAmount() {
        return orderSampleAmount;
    }

    public void setOrderSampleAmount(String orderSampleAmount) {
        this.orderSampleAmount = orderSampleAmount;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public ArrayList<Fabric> getFabrics() {
        return fabrics;
    }

    public void setFabrics(ArrayList<Fabric> fabrics) {
        this.fabrics = fabrics;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
