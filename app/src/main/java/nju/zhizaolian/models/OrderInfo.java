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
    private String total;//样衣制作金
    private String type;//样衣制作金
    private String taskName;
    private String price;
    private ArrayList<DeliveryRecord> deliveryRecords;
    private ArrayList<Produce> produceArrayList;
    private String verifyQuoteComment;//报价时的意见


    public OrderInfo() {
    }

    public static OrderInfo fromJson(JSONObject jsonObject){
        OrderInfo orderInfo=new OrderInfo();
        try{
            orderInfo.taskId=jsonObject.getString("taskId");

            orderInfo.logistics=jsonObject.has("logistics")?Logistics.fromJson(jsonObject.getJSONObject("logistics")):null;

            orderInfo.craft= jsonObject.has("craft")?Craft.fromJson(jsonObject.getJSONObject("craft")):null;

            orderInfo.accessories=jsonObject.has("accessorys")?Accessory.fromJson(jsonObject.getJSONArray("accessorys")):null;

            orderInfo.total=jsonObject.has("total")?jsonObject.getString("total"):null;
            orderInfo.type=jsonObject.has("type")?jsonObject.getString("type"):null;
            orderInfo.taskName=jsonObject.has("taskName")?jsonObject.getString("taskName"):null;
            orderInfo.price=jsonObject.has("price")?jsonObject.getString("price"):null;

            orderInfo.custom=Custom.fromJson(jsonObject.getJSONObject("customer"));
            orderInfo.deliveryRecords=jsonObject.has("deliveryRecord")?DeliveryRecord.fromJson(jsonObject.getJSONArray("deliveryRecord")):null;
            orderInfo.produceArrayList=jsonObject.has("produce")?Produce.fromJson(jsonObject.getJSONArray("produce")):null;

            orderInfo.fabricCosts=jsonObject.has("fabricCosts")?FabricCost.fromJson(jsonObject.getJSONArray("fabricCosts")):null;
            orderInfo.verifyQuoteComment=jsonObject.has("verifyQuoteComment")?jsonObject.getString("verifyQuoteComment"):null;


           orderInfo.versionDatas=jsonObject.has("versions")?VersionData.fromJson(jsonObject.getJSONArray("versions")):null;



            orderInfo.processInstanceId=jsonObject.getString("processInstanceId");



           orderInfo.order=Order.fromJson(jsonObject.getJSONObject("order"));


          orderInfo.accessoryCosts=jsonObject.has("accessoryCosts")?AccessoryCost.fromJson(jsonObject.getJSONArray("accessoryCosts")):null;


            orderInfo.orderSampleAmount=jsonObject.has("orderSampleAmount")?jsonObject.getString("orderSampleAmount"):null;


           orderInfo.quote= jsonObject.has("quote")?Quote.fromJson(jsonObject.getJSONObject("quote")):null;


                orderInfo.fabrics=jsonObject.has("fabrics")?Fabric.fromJson(jsonObject.getJSONArray("fabrics")):null;


            orderInfo.employee=jsonObject.has("employee")?Employee.fromJson(jsonObject.getJSONObject("employee")):null;

            orderInfo.orderId=jsonObject.getString("orderId");

        }catch (Exception e){
            e.printStackTrace();

        }



        return orderInfo;
    }

    public String getVerifyQuoteComment() {
        return verifyQuoteComment;
    }

    public void setVerifyQuoteComment(String verifyQuoteComment) {
        this.verifyQuoteComment = verifyQuoteComment;
    }

    public ArrayList<Produce> getProduceArrayList() {
        return produceArrayList;
    }

    public void setProduceArrayList(ArrayList<Produce> produceArrayList) {
        this.produceArrayList = produceArrayList;
    }

    public ArrayList<DeliveryRecord> getDeliveryRecords() {
        return deliveryRecords;
    }

    public void setDeliveryRecords(ArrayList<DeliveryRecord> deliveryRecords) {
        this.deliveryRecords = deliveryRecords;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
