package nju.zhizaolian.models;

import org.json.JSONObject;

/**
 * Created by lk on 15/5/3.
 */
public class Package {

    private Integer packageId;
    private Integer orderId;
    private String packageTime;
    private String warehouseId;
    private String shelfId;
    private String location;

    public Package() {
    }
    public static Package fromJson(JSONObject jsonObject){
        Package aPackage=new Package();
        return aPackage;
    }


    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPackageTime() {
        return packageTime;
    }

    public void setPackageTime(String packageTime) {
        this.packageTime = packageTime;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
