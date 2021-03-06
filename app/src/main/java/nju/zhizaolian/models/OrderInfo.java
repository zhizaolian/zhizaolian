package nju.zhizaolian.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 15/4/27.
 */
public class OrderInfo implements Serializable {
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
    private String moneyName;//现金名称
    private String samplePrice;
    private String url;
    private ArrayList<PackageDetail> packageDetailArrayList=new ArrayList<PackageDetail>();
    private String deposit;
    private String packageNumber;

    private List<Produce> sample;
    private List<Produce> produce;
    private DesignCad designCad;
    private List<RepairRecord> repairRecords;
    public OrderInfo() {
    }

    public static OrderInfo fromJson(JSONObject jsonObject){
        OrderInfo orderInfo=new OrderInfo();
        try{
            orderInfo.taskId=jsonObject.has("taskId")?jsonObject.getString("taskId"):"";

            orderInfo.logistics=jsonObject.has("logistics")?Logistics.fromJson(jsonObject.getJSONObject("logistics")):null;

            orderInfo.craft= jsonObject.has("craft")?Craft.fromJson(jsonObject.getJSONObject("craft")):null;

            orderInfo.accessories=jsonObject.has("accessorys")?Accessory.fromJson(jsonObject.getJSONArray("accessorys")):null;

            orderInfo.total=jsonObject.has("total")?jsonObject.getString("total"):null;
            orderInfo.type=jsonObject.has("type")?jsonObject.getString("type"):null;
            orderInfo.taskName=jsonObject.has("taskName")?jsonObject.getString("taskName"):null;
            orderInfo.price=jsonObject.has("price")?jsonObject.getString("price"):null;

            orderInfo.custom=jsonObject.has("customer")?Custom.fromJson(jsonObject.getJSONObject("customer")):null;
            orderInfo.deliveryRecords=jsonObject.has("deliveryRecord")?DeliveryRecord.fromJson(jsonObject.getJSONArray("deliveryRecord")):null;
            orderInfo.produceArrayList=jsonObject.has("produce")?Produce.fromJson(jsonObject.getJSONArray("produce")):null;

            orderInfo.fabricCosts=jsonObject.has("fabricCosts")?FabricCost.fromJson(jsonObject.getJSONArray("fabricCosts")):null;
            orderInfo.verifyQuoteComment=jsonObject.has("verifyQuoteComment")?jsonObject.getString("verifyQuoteComment"):null;


           orderInfo.versionDatas=jsonObject.has("versions")?VersionData.fromJson(jsonObject.getJSONArray("versions")):null;



           orderInfo.processInstanceId=jsonObject.has("processInstanceId")?jsonObject.getString("processInstanceId"):"";
            orderInfo.sample=jsonObject.has("sample")?Produce.fromJson(jsonObject.getJSONArray("sample")):null;
            orderInfo.produce=jsonObject.has("produce")?Produce.fromJson(jsonObject.getJSONArray("produce")):null;
            orderInfo.designCad=jsonObject.has("designCad")?DesignCad.fromJson(jsonObject.getJSONObject("designCad")):null;



            orderInfo.order=Order.fromJson(jsonObject.getJSONObject("order"));


          orderInfo.accessoryCosts=jsonObject.has("accessoryCosts")?AccessoryCost.fromJson(jsonObject.getJSONArray("accessoryCosts")):null;


          orderInfo.orderSampleAmount=jsonObject.has("orderSampleAmount")?jsonObject.getString("orderSampleAmount"):null;

            if(jsonObject.has("quote")){
                if(jsonObject.getString("quote").equals("null")){
                    orderInfo.quote=null;
                }else {
                    orderInfo.quote=Quote.fromJson(jsonObject.getJSONObject("quote"));
                }
            }



           orderInfo.fabrics=jsonObject.has("fabrics")?Fabric.fromJson(jsonObject.getJSONArray("fabrics")):null;

          orderInfo.packageNumber=jsonObject.has("packageNumber")?jsonObject.getString("packageNumber"):null;
            orderInfo.employee=jsonObject.has("employee")?Employee.fromJson(jsonObject.getJSONObject("employee")):null;
            orderInfo.deposit=jsonObject.has("deposit")?jsonObject.getString("deposit"):null;
            orderInfo.orderId=jsonObject.getString("orderId");
            orderInfo.moneyName=jsonObject.has("moneyName")?jsonObject.getString("moneyName"):null;
            if(jsonObject.has("packageDetails")){
                for(int i=0 ;i<jsonObject.getJSONArray("packageDetails").length();i++){
                    ArrayList<PackageDetail> packageDetailItem=PackageDetail.fromJson(jsonObject.getJSONArray("packageDetails").getJSONArray(i));
                    orderInfo.packageDetailArrayList.addAll(packageDetailItem);
                }

            }

            orderInfo.samplePrice= jsonObject.has("samplePrice")?jsonObject.getString("samplePrice"):null;
            orderInfo.url=jsonObject.has("url")?jsonObject.getString("url"):null;
            orderInfo.repairRecords=jsonObject.has("repairRecord")?RepairRecord.fromJson(jsonObject.getJSONArray("repairRecord")):null;
        }catch (Exception e){
            e.printStackTrace();

        }



        return orderInfo;
    }

    public List<RepairRecord> getRepairRecords() {
        return repairRecords;
    }

    public void setRepairRecords(List<RepairRecord> repairRecords) {
        this.repairRecords = repairRecords;
    }

    public List<Produce> getSample() {
        return sample;
    }

    public void setSample(List<Produce> sample) {
        this.sample = sample;
    }

    public List<Produce> getProduce() {
        return produce;
    }

    public void setProduce(List<Produce> produce) {
        this.produce = produce;
    }

    public DesignCad getDesignCad() {
        return designCad;
    }

    public void setDesignCad(DesignCad designCad) {
        this.designCad = designCad;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public ArrayList<PackageDetail> getPackageDetailArrayList() {
        return packageDetailArrayList;
    }

    public void setPackageDetailArrayList(ArrayList<PackageDetail> packageDetailArrayList) {
        this.packageDetailArrayList = packageDetailArrayList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(String samplePrice) {
        this.samplePrice = samplePrice;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName;
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
