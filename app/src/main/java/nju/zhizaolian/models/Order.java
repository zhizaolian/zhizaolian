package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by soft on 2015/4/17.
 */
public class Order {
    // Fields
    private boolean buySweaterMaterialResult;//毛衣订单仓是否需要买毛衣原料
    private String  orderProcessStateName;//订单当前执行到的流程节点的状态
    private String  confirmDepositFile;//大货定金收据电子图片
    private String  confirmFinalPaymentFile;//大货尾金收据电子图片
    private String  confirmSampleMoneyFile;//样衣金收据电子图片
    private Short   isHaoDuoYi;

    private Integer orderId;
    private Short reorder;
    private Integer customerId;
    private Integer employeeId;
    private String orderState;//A代表正在进行，Done代表正常结束，1代表被终止订单
    private Timestamp orderTime;
    private String customerName;
    private String customerCompany;
    private String customerCompanyFax;
    private String customerPhone1;
    private String customerPhone2;
    private String customerCompanyAddress;
    private String styleName;
    private String clothesType;
    private String fabricType;
    private String styleSex;
    private String styleSeason;
    private String specialProcess;
    private String otherRequirements;
    private String referenceUrl;
    private String sampleClothesPicture;
    private String sampleClothesThumbnailPicture;//样衣缩略图
    private String referencePicture;
    private Integer askAmount;
    private Integer sampleAmount;
    private String processId;

    private String Purchase_director; //采购负责人
    private String supplier;//供应商
    private String Purchase_time;//采购时间
    private String Wool_type;//毛线类型
    private String Wool_weight;//重量
    private String total_price;//总价
    public Order(){

    }
    public static Order fromJson(JSONObject jsonObject){
        Order order=new Order();
        try {
            order.askAmount=jsonObject.getInt("");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return order;
    }
    public static ArrayList<Order> fromJson(JSONArray jsonArray){
        ArrayList<Order>  orderArrayList=new ArrayList<Order>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
           JSONObject jsonObject=null;
            try {
                jsonObject=jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Order order=Order.fromJson(jsonObject);
            if(order != null){
                orderArrayList.add(order);
            }
        }
        return orderArrayList;
    }
    public boolean isBuySweaterMaterialResult() {
        return buySweaterMaterialResult;
    }

    public void setBuySweaterMaterialResult(boolean buySweaterMaterialResult) {
        this.buySweaterMaterialResult = buySweaterMaterialResult;
    }

    public String getConfirmDepositFile() {
        return confirmDepositFile;
    }

    public void setConfirmDepositFile(String confirmDepositFile) {
        this.confirmDepositFile = confirmDepositFile;
    }

    public String getOrderProcessStateName() {
        return orderProcessStateName;
    }

    public void setOrderProcessStateName(String orderProcessStateName) {
        this.orderProcessStateName = orderProcessStateName;
    }

    public String getConfirmFinalPaymentFile() {
        return confirmFinalPaymentFile;
    }

    public void setConfirmFinalPaymentFile(String confirmFinalPaymentFile) {
        this.confirmFinalPaymentFile = confirmFinalPaymentFile;
    }

    public String getConfirmSampleMoneyFile() {
        return confirmSampleMoneyFile;
    }

    public void setConfirmSampleMoneyFile(String confirmSampleMoneyFile) {
        this.confirmSampleMoneyFile = confirmSampleMoneyFile;
    }

    public Short getIsHaoDuoYi() {
        return isHaoDuoYi;
    }

    public void setIsHaoDuoYi(Short isHaoDuoYi) {
        this.isHaoDuoYi = isHaoDuoYi;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Short getReorder() {
        return reorder;
    }

    public void setReorder(Short reorder) {
        this.reorder = reorder;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCompanyFax() {
        return customerCompanyFax;
    }

    public void setCustomerCompanyFax(String customerCompanyFax) {
        this.customerCompanyFax = customerCompanyFax;
    }

    public String getCustomerPhone1() {
        return customerPhone1;
    }

    public void setCustomerPhone1(String customerPhone1) {
        this.customerPhone1 = customerPhone1;
    }

    public String getCustomerPhone2() {
        return customerPhone2;
    }

    public void setCustomerPhone2(String customerPhone2) {
        this.customerPhone2 = customerPhone2;
    }

    public String getCustomerCompanyAddress() {
        return customerCompanyAddress;
    }

    public void setCustomerCompanyAddress(String customerCompanyAddress) {
        this.customerCompanyAddress = customerCompanyAddress;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getClothesType() {
        return clothesType;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getStyleSex() {
        return styleSex;
    }

    public void setStyleSex(String styleSex) {
        this.styleSex = styleSex;
    }

    public String getSpecialProcess() {
        return specialProcess;
    }

    public void setSpecialProcess(String specialProcess) {
        this.specialProcess = specialProcess;
    }

    public String getStyleSeason() {
        return styleSeason;
    }

    public void setStyleSeason(String styleSeason) {
        this.styleSeason = styleSeason;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getSampleClothesPicture() {
        return sampleClothesPicture;
    }

    public void setSampleClothesPicture(String sampleClothesPicture) {
        this.sampleClothesPicture = sampleClothesPicture;
    }

    public String getSampleClothesThumbnailPicture() {
        return sampleClothesThumbnailPicture;
    }

    public void setSampleClothesThumbnailPicture(String sampleClothesThumbnailPicture) {
        this.sampleClothesThumbnailPicture = sampleClothesThumbnailPicture;
    }

    public String getReferencePicture() {
        return referencePicture;
    }

    public void setReferencePicture(String referencePicture) {
        this.referencePicture = referencePicture;
    }

    public Integer getAskAmount() {
        return askAmount;
    }

    public void setAskAmount(Integer askAmount) {
        this.askAmount = askAmount;
    }

    public Integer getSampleAmount() {
        return sampleAmount;
    }

    public void setSampleAmount(Integer sampleAmount) {
        this.sampleAmount = sampleAmount;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPurchase_director() {
        return Purchase_director;
    }

    public void setPurchase_director(String purchase_director) {
        Purchase_director = purchase_director;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getPurchase_time() {
        return Purchase_time;
    }

    public void setPurchase_time(String purchase_time) {
        Purchase_time = purchase_time;
    }

    public String getWool_weight() {
        return Wool_weight;
    }

    public void setWool_weight(String wool_weight) {
        Wool_weight = wool_weight;
    }

    public String getWool_type() {
        return Wool_type;
    }

    public void setWool_type(String wool_type) {
        Wool_type = wool_type;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
