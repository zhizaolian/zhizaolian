package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by lk on 15/3/3.
 */
public class Custom implements Serializable {
    private Integer customerId;
    private String companyId;
    private String companyName;
    private String customerName;
    private String province;
    private String city;
    private String websiteUrl;
    private String websiteType;
    private String companyAddress;
    private String companyFax;
    private String companyPhone;
    private String buyContact;
    private String contactPhone1;
    private String contactPhone2;
    private String qq;
    private String email;
    private String customerPhone;
    private String bossName;
    private String bossPhone;
    private String registerDate;
    private Integer registerEmployeeId;

    public Custom() {
    }

    public static  Custom fromJson(JSONObject jsonObject){
        Custom custom=new Custom();
        try{
            custom.customerId=jsonObject.getInt("customerId");
            custom.companyId=jsonObject.getString("companyId");
            custom.province=jsonObject.getString("province");
            custom.websiteUrl=jsonObject.getString("websiteUrl");
            custom.websiteType=jsonObject.getString("websiteType");
            custom.buyContact=jsonObject.getString("buyContact");
            custom.contactPhone1=jsonObject.getString("contactPhone1");
            custom.contactPhone2=jsonObject.getString("contactPhone2");
            custom.qq=jsonObject.getString("qq");
            custom.email=jsonObject.getString("email");
            custom.bossPhone= jsonObject.getString("bossPhone");
            custom.registerEmployeeId=jsonObject.getInt("registerEmployeeId");
            custom.customerName=jsonObject.getString("customerName");
            custom.customerPhone=jsonObject.getString("customerPhone");
            custom.companyName=jsonObject.getString("companyName");
            custom.companyPhone=jsonObject.getString("companyPhone");
            custom.companyAddress=jsonObject.getString("companyAddress");
            custom.bossName=jsonObject.getString("bossName");
            custom.city=jsonObject.getString("city");
            custom.companyFax=jsonObject.getString("companyFax");
            custom.registerDate=jsonObject.getString("registerDate");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return custom;
    }
    public static ArrayList<Custom> fromJson(JSONArray jsonArray){
        ArrayList<Custom> customArrayList=new ArrayList<Custom>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Custom custom=Custom.fromJson(jsonObject);
            if(custom != null){
                customArrayList.add(custom);
            }
        }
        return  customArrayList;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteType() {
        return websiteType;
    }

    public void setWebsiteType(String websiteType) {
        this.websiteType = websiteType;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getBuyContact() {
        return buyContact;
    }

    public void setBuyContact(String buyContact) {
        this.buyContact = buyContact;
    }

    public String getContactPhone1() {
        return contactPhone1;
    }

    public void setContactPhone1(String contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactPhone2() {
        return contactPhone2;
    }

    public void setContactPhone2(String contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossPhone() {
        return bossPhone;
    }

    public void setBossPhone(String bossPhone) {
        this.bossPhone = bossPhone;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getRegisterEmployeeId() {
        return registerEmployeeId;
    }

    public void setRegisterEmployeeId(Integer registerEmployeeId) {
        this.registerEmployeeId = registerEmployeeId;
    }


}
