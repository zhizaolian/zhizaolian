package nju.zhizaolian.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lk on 15/4/27.
 */
public class Quote {
    // Fields

    private Integer orderId;
    private Float designCost = (float) 0; //原为设计费用，现为订单的大货物流费
    private Float craftCost = (float) 0; // 单件工艺费用
    private Float cutCost = (float) 0; // 裁剪费用
    private Float manageCost = (float) 0; // 管理费用
    private Float swingCost = (float) 0; // 缝制费用
    private Float ironingCost = (float) 0; // 整烫费用
    private Float nailCost = (float) 0; // 锁订费用
    private Float packageCost = (float) 0; // 包装费用
    private Float otherCost = (float) 0; // 其他费用
    private Float fabricCost = (float) 0; // 面料费用
    private Float accessoryCost = (float) 0; // 辅料费用
    private Float singleCost = (float) 0; // 单件成本
    private String profitPerPiece;
    private String innerPrice;
    private String outerPrice;


    public Quote() {
    }

    public String getOuterPrice() {
        return outerPrice;
    }

    public void setOuterPrice(String outerPrice) {
        this.outerPrice = outerPrice;
    }

    public String getInnerPrice() {
        return innerPrice;
    }

    public void setInnerPrice(String innerPrice) {
        this.innerPrice = innerPrice;
    }

    public String getProfitPerPiece() {
        return profitPerPiece;
    }

    public void setProfitPerPiece(String profitPerPiece) {
        this.profitPerPiece = profitPerPiece;
    }

    public static Quote fromJson(JSONObject jsonObject){
        Quote quote=new Quote();
        try {
            quote.accessoryCost= Float.valueOf(jsonObject.getString("accessoryCost"));
            quote.craftCost= Float.valueOf(jsonObject.getString("craftCost"));
            quote.cutCost= Float.valueOf(jsonObject.getString("cutCost"));
            quote.designCost= Float.valueOf(jsonObject.getString("designCost"));
            quote.fabricCost= Float.valueOf(jsonObject.getString("fabricCost"));
            quote.innerPrice=jsonObject.getString("innerPrice");
            quote.ironingCost= Float.valueOf(jsonObject.getString("ironingCost"));
            quote.manageCost= Float.valueOf(jsonObject.getString("manageCost"));
            quote.nailCost= Float.valueOf(jsonObject.getString("nailCost"));
            quote.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            quote.otherCost= Float.valueOf(jsonObject.getString("otherCost"));
           quote.outerPrice=jsonObject.getString("outerPrice");
            quote.packageCost= Float.valueOf(jsonObject.getString("packageCost"));
            quote.profitPerPiece=jsonObject.getString("profitPerPiece");
            quote.singleCost= Float.valueOf(jsonObject.getString("singleCost"));
            quote.swingCost= Float.valueOf(jsonObject.getString("swingCost"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quote;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Float getDesignCost() {
        return designCost;
    }

    public void setDesignCost(Float designCost) {
        this.designCost = designCost;
    }

    public Float getCraftCost() {
        return craftCost;
    }

    public void setCraftCost(Float craftCost) {
        this.craftCost = craftCost;
    }

    public Float getCutCost() {
        return cutCost;
    }

    public void setCutCost(Float cutCost) {
        this.cutCost = cutCost;
    }

    public Float getManageCost() {
        return manageCost;
    }

    public void setManageCost(Float manageCost) {
        this.manageCost = manageCost;
    }

    public Float getSwingCost() {
        return swingCost;
    }

    public void setSwingCost(Float swingCost) {
        this.swingCost = swingCost;
    }

    public Float getIroningCost() {
        return ironingCost;
    }

    public void setIroningCost(Float ironingCost) {
        this.ironingCost = ironingCost;
    }

    public Float getNailCost() {
        return nailCost;
    }

    public void setNailCost(Float nailCost) {
        this.nailCost = nailCost;
    }

    public Float getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(Float packageCost) {
        this.packageCost = packageCost;
    }

    public Float getFabricCost() {
        return fabricCost;
    }

    public void setFabricCost(Float fabricCost) {
        this.fabricCost = fabricCost;
    }

    public Float getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Float otherCost) {
        this.otherCost = otherCost;
    }

    public Float getAccessoryCost() {
        return accessoryCost;
    }

    public void setAccessoryCost(Float accessoryCost) {
        this.accessoryCost = accessoryCost;
    }

    public Float getSingleCost() {
        return singleCost;
    }

    public void setSingleCost(Float singleCost) {
        this.singleCost = singleCost;
    }
}
