package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/27.
 */
public class VersionData {
    private Integer vid;
    private Integer orderId;
    private String size;
    private String centerBackLength;
    private String bust;
    private String waistline;
    private String shoulder;
    private String buttock;
    private String hem;
    private String trousers;
    private String skirt;
    private String sleeves;

    // Constructors

    /** default constructor */
    public VersionData() {
    }

    /** full constructor */
    public VersionData(Integer orderId, String size, String centerBackLength,
                       String bust, String waistline, String shoulder, String buttock,
                       String hem, String trousers, String skirt, String sleeves) {
        this.orderId = orderId;
        this.size = size;
        this.centerBackLength = centerBackLength;
        this.bust = bust;
        this.waistline = waistline;
        this.shoulder = shoulder;
        this.buttock = buttock;
        this.hem = hem;
        this.trousers = trousers;
        this.skirt = skirt;
        this.sleeves = sleeves;
    }
    public static  VersionData fromJson(JSONObject jsonObject){
        VersionData versionData=new VersionData();
        try {
            versionData.bust=jsonObject.getString("bust");
            versionData.buttock=jsonObject.getString("buttock");
            versionData.centerBackLength=jsonObject.getString("centerBackLength");
            versionData.hem=jsonObject.getString("hem");
            versionData.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            versionData.shoulder=jsonObject.getString("shoulder");
            versionData.size=jsonObject.getString("size");
            versionData.skirt=jsonObject.getString("skirt");
            versionData.sleeves=jsonObject.getString("sleeves");
            versionData.trousers=jsonObject.getString("trousers");
            versionData.vid= Integer.valueOf(jsonObject.getString("vid"));
            versionData.waistline=jsonObject.getString("waistline");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  versionData;
    }

    public static ArrayList<VersionData> fromJson(JSONArray jsonArray){
        ArrayList<VersionData> versionDataArrayList=new ArrayList<VersionData>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            VersionData versionData=VersionData.fromJson(jsonObject);
            if(versionData != null){
                versionDataArrayList.add(versionData);
            }
        }

        return versionDataArrayList;
    }





    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCenterBackLength() {
        return centerBackLength;
    }

    public void setCenterBackLength(String centerBackLength) {
        this.centerBackLength = centerBackLength;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getShoulder() {
        return shoulder;
    }

    public void setShoulder(String shoulder) {
        this.shoulder = shoulder;
    }

    public String getButtock() {
        return buttock;
    }

    public void setButtock(String buttock) {
        this.buttock = buttock;
    }

    public String getHem() {
        return hem;
    }

    public void setHem(String hem) {
        this.hem = hem;
    }

    public String getTrousers() {
        return trousers;
    }

    public void setTrousers(String trousers) {
        this.trousers = trousers;
    }

    public String getSkirt() {
        return skirt;
    }

    public void setSkirt(String skirt) {
        this.skirt = skirt;
    }

    public String getSleeves() {
        return sleeves;
    }

    public void setSleeves(String sleeves) {
        this.sleeves = sleeves;
    }
}
