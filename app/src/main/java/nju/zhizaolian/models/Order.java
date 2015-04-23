package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by soft on 2015/4/17.
 */
public class Order implements Serializable{
    // Fields
    private String askAmount;
    private String askCodeNumber;
    private String askDeliverDate;
    private String askProducePeriod;
    private String buySweaterMaterialResult;
    private String clothesType;
    private String confirmDepositFile;
    private String confirmFinalPaymentFile;
    private String confirmSampleMoneyFile;
    private String contractFile;
    private String customerCompany;
    private String customerCompanyAddress;
    private String customerCompanyFax;
    private String customerId;
    private String customerName;
    private String customerPhone1;
    private String customerPhone2;
    private String discount;
    private String employeeId;
    private String fabricType;
    private String hasPostedSampleClothes;
    private String isHaoDuoYi;
    private String isNeedSampleClothes;
    private String logisticsState;
    private String masspurDate;
    private String masspurName;
    private String masssupplierName;
    private String moneyremark;
    private String orderId;
    private String orderProcessStateName;
    private String orderSource;
    private String orderState;
    private String orderTime;
    private String otherRequirements;
    private String payAccountInfo;
    private String processId;
    private String purchase_director;
    private String purchase_time;
    private String referencePicture;
    private String referenceUrl;
    private String reorder;
    private String sampleAmount;
    private String sampleClothesPicture;
    private String sampleClothesThumbnailPicture;
    private String sampleMoney;
    private String samplepurDate;
    private String samplepurName;
    private String samplesupplierName;
    private String specialProcess;
    private String styleName;
    private String styleSeason;
    private String styleSex;
    private String supplier;
    private String totalMoney;
    private String total_price;
    private String wool_type;
    private String wool_weight;
    public Order(){

    }
    public static Order fromJson(JSONObject jsonObject){
        Order order=new Order();
        try {
            order.askAmount=jsonObject.getString("askAmount");
            order.askCodeNumber=jsonObject.getString("askCodeNumber");
            order.askDeliverDate=jsonObject.getString("askDeliverDate");
            order.askProducePeriod=jsonObject.getString("askProducePeriod");
            order.buySweaterMaterialResult=jsonObject.getString("buySweaterMaterialResult");
            order.clothesType=jsonObject.getString("clothesType");
            order.confirmDepositFile=jsonObject.getString("confirmDepositFile");
            order.confirmFinalPaymentFile=jsonObject.getString("confirmFinalPaymentFile");
            order.confirmSampleMoneyFile=jsonObject.getString("confirmSampleMoneyFile");
            order.contractFile=jsonObject.getString("contractFile");
            order.customerCompany=jsonObject.getString("customerCompany");
            order.customerCompanyAddress=jsonObject.getString("customerCompanyAddress");
            order.customerCompanyFax=jsonObject.getString("customerCompanyFax");
            order.customerId=jsonObject.getString("customerId");
            order.customerName=jsonObject.getString("customerName");
            order.customerPhone1=jsonObject.getString("customerPhone1");
            order.customerPhone2=jsonObject.getString("customerPhone2");
            order.discount=jsonObject.getString("discount");
            order.employeeId=jsonObject.getString("employeeId");
            order.fabricType=jsonObject.getString("fabricType");
            order.hasPostedSampleClothes=jsonObject.getString("hasPostedSampleClothes");
            order.isHaoDuoYi=jsonObject.getString("isHaoDuoYi");
            order.isNeedSampleClothes=jsonObject.getString("isNeedSampleClothes");
            order.logisticsState=jsonObject.getString("logisticsState");
            order.masspurDate=jsonObject.getString("masspurDate");
            order.masspurName=jsonObject.getString("masspurName");
            order.masssupplierName=jsonObject.getString("masssupplierName");
            order.moneyremark=jsonObject.getString("moneyremark");
            order.orderId=jsonObject.getString("orderId");
            order.orderProcessStateName=jsonObject.getString("orderProcessStateName");
            order.orderSource=jsonObject.getString("orderSource");
            order.orderState=jsonObject.getString("orderState");
            order.orderTime=jsonObject.getString("orderTime");
            order.otherRequirements=jsonObject.getString("otherRequirements");
            order.payAccountInfo=jsonObject.getString("payAccountInfo");
            order.processId=jsonObject.getString("processId");
            order.purchase_director=jsonObject.getString("purchase_director");
            order.purchase_time=jsonObject.getString("purchase_time");
            order.referencePicture=jsonObject.getString("referencePicture");
            order.referenceUrl=jsonObject.getString("referenceUrl");
            order.reorder=jsonObject.getString("reorder");
            order.sampleAmount=jsonObject.getString("sampleAmount");
            order.sampleClothesPicture=jsonObject.getString("sampleClothesPicture");
            order.sampleClothesThumbnailPicture=jsonObject.getString("sampleClothesThumbnailPicture");
            order.sampleMoney=jsonObject.getString("sampleMoney");
            order.samplepurDate=jsonObject.getString("samplepurDate");
            order.samplepurName=jsonObject.getString("samplepurName");
            order.samplesupplierName=jsonObject.getString("samplesupplierName");
            order.specialProcess=jsonObject.getString("specialProcess");
            order.styleName=jsonObject.getString("styleName");
            order.styleSeason=jsonObject.getString("styleSeason");
            order.styleSex=jsonObject.getString("styleSex");
            order.supplier=jsonObject.getString("supplier");
            order.totalMoney=jsonObject.getString("totalMoney");
            order.total_price=jsonObject.getString("total_price");
            order.wool_type=jsonObject.getString("wool_type");
            order.wool_weight=jsonObject.getString("wool_weight");
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

    public String getAskAmount() {
        return askAmount;
    }

    public void setAskAmount(String askAmount) {
        this.askAmount = askAmount;
    }

    public String getAskCodeNumber() {
        return askCodeNumber;
    }

    public void setAskCodeNumber(String askCodeNumber) {
        this.askCodeNumber = askCodeNumber;
    }

    public String getAskDeliverDate() {
        return askDeliverDate;
    }

    public void setAskDeliverDate(String askDeliverDate) {
        this.askDeliverDate = askDeliverDate;
    }

    public String getAskProducePeriod() {
        return askProducePeriod;
    }

    public void setAskProducePeriod(String askProducePeriod) {
        this.askProducePeriod = askProducePeriod;
    }

    public String getBuySweaterMaterialResult() {
        return buySweaterMaterialResult;
    }

    public void setBuySweaterMaterialResult(String buySweaterMaterialResult) {
        this.buySweaterMaterialResult = buySweaterMaterialResult;
    }

    public String getClothesType() {
        return clothesType;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public String getConfirmDepositFile() {
        return confirmDepositFile;
    }

    public void setConfirmDepositFile(String confirmDepositFile) {
        this.confirmDepositFile = confirmDepositFile;
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

    public String getContractFile() {
        return contractFile;
    }

    public void setContractFile(String contractFile) {
        this.contractFile = contractFile;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getCustomerCompanyAddress() {
        return customerCompanyAddress;
    }

    public void setCustomerCompanyAddress(String customerCompanyAddress) {
        this.customerCompanyAddress = customerCompanyAddress;
    }

    public String getCustomerCompanyFax() {
        return customerCompanyFax;
    }

    public void setCustomerCompanyFax(String customerCompanyFax) {
        this.customerCompanyFax = customerCompanyFax;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getHasPostedSampleClothes() {
        return hasPostedSampleClothes;
    }

    public void setHasPostedSampleClothes(String hasPostedSampleClothes) {
        this.hasPostedSampleClothes = hasPostedSampleClothes;
    }

    public String getIsHaoDuoYi() {
        return isHaoDuoYi;
    }

    public void setIsHaoDuoYi(String isHaoDuoYi) {
        this.isHaoDuoYi = isHaoDuoYi;
    }

    public String getIsNeedSampleClothes() {
        return isNeedSampleClothes;
    }

    public void setIsNeedSampleClothes(String isNeedSampleClothes) {
        this.isNeedSampleClothes = isNeedSampleClothes;
    }

    public String getLogisticsState() {
        return logisticsState;
    }

    public void setLogisticsState(String logisticsState) {
        this.logisticsState = logisticsState;
    }

    public String getMasspurDate() {
        return masspurDate;
    }

    public void setMasspurDate(String masspurDate) {
        this.masspurDate = masspurDate;
    }

    public String getMasspurName() {
        return masspurName;
    }

    public void setMasspurName(String masspurName) {
        this.masspurName = masspurName;
    }

    public String getMasssupplierName() {
        return masssupplierName;
    }

    public void setMasssupplierName(String masssupplierName) {
        this.masssupplierName = masssupplierName;
    }

    public String getMoneyremark() {
        return moneyremark;
    }

    public void setMoneyremark(String moneyremark) {
        this.moneyremark = moneyremark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderProcessStateName() {
        return orderProcessStateName;
    }

    public void setOrderProcessStateName(String orderProcessStateName) {
        this.orderProcessStateName = orderProcessStateName;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public String getPayAccountInfo() {
        return payAccountInfo;
    }

    public void setPayAccountInfo(String payAccountInfo) {
        this.payAccountInfo = payAccountInfo;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getPurchase_director() {
        return purchase_director;
    }

    public void setPurchase_director(String purchase_director) {
        this.purchase_director = purchase_director;
    }

    public String getPurchase_time() {
        return purchase_time;
    }

    public void setPurchase_time(String purchase_time) {
        this.purchase_time = purchase_time;
    }

    public String getReferencePicture() {
        return referencePicture;
    }

    public void setReferencePicture(String referencePicture) {
        this.referencePicture = referencePicture;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getReorder() {
        return reorder;
    }

    public void setReorder(String reorder) {
        this.reorder = reorder;
    }

    public String getSampleAmount() {
        return sampleAmount;
    }

    public void setSampleAmount(String sampleAmount) {
        this.sampleAmount = sampleAmount;
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

    public String getSampleMoney() {
        return sampleMoney;
    }

    public void setSampleMoney(String sampleMoney) {
        this.sampleMoney = sampleMoney;
    }

    public String getSamplepurDate() {
        return samplepurDate;
    }

    public void setSamplepurDate(String samplepurDate) {
        this.samplepurDate = samplepurDate;
    }

    public String getSamplepurName() {
        return samplepurName;
    }

    public void setSamplepurName(String samplepurName) {
        this.samplepurName = samplepurName;
    }

    public String getSamplesupplierName() {
        return samplesupplierName;
    }

    public void setSamplesupplierName(String samplesupplierName) {
        this.samplesupplierName = samplesupplierName;
    }

    public String getSpecialProcess() {
        return specialProcess;
    }

    public void setSpecialProcess(String specialProcess) {
        this.specialProcess = specialProcess;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleSeason() {
        return styleSeason;
    }

    public void setStyleSeason(String styleSeason) {
        this.styleSeason = styleSeason;
    }

    public String getStyleSex() {
        return styleSex;
    }

    public void setStyleSex(String styleSex) {
        this.styleSex = styleSex;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getWool_type() {
        return wool_type;
    }

    public void setWool_type(String wool_type) {
        this.wool_type = wool_type;
    }

    public String getWool_weight() {
        return wool_weight;
    }

    public void setWool_weight(String wool_weight) {
        this.wool_weight = wool_weight;
    }
}
