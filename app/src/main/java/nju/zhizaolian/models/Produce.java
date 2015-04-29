package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ColorfulCode on 2015/4/29.
 */
public class Produce {
    private String color;
    private String xs;
    private String s;
    private String m;
    private String l;
    private String xl;
    private String xxl;
    private String j;
    private String oid;
    private String pid;
    private String processing_side;
    private String produceAmount;
    private String purchase_director;
    private String sendTime;
    private String type;


    public static Produce fromJson(JSONObject object){
        Produce produce =new Produce();
        try {
            produce.color = object.getString("color");
            produce.xs = object.getString("xs");
            produce.s = object.getString("s");
            produce.m = object.getString("m");
            produce.l = object.getString("l");
            produce.xl = object.getString("xl");
            produce.xxl = object.getString("xxl");
            produce.j = object.getString("j");
            produce.oid = object.getString("oid");
            produce.pid = object.getString("pid");
            produce.processing_side = object.getString("processing_side");
            produce.produceAmount = object.getString("produceAmount");
            produce.purchase_director = object.getString("purchase_director");
            produce.sendTime = object.getString("sendTime");
            produce.type = object.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return produce;
    }

    public static ArrayList<Produce> fromJson(JSONArray jsonArray){

        ArrayList<Produce> list=new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object = null;
                try {
                    object=jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Produce produce = fromJson(object);
                if(produce!=null){
                    list.add(produce);
                }
            }
            return list;

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXxl() {
        return xxl;
    }

    public void setXxl(String xxl) {
        this.xxl = xxl;
    }

    public String getJ() {
        return j;
    }

    public void setJ(String j) {
        this.j = j;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProcessing_side() {
        return processing_side;
    }

    public void setProcessing_side(String processing_side) {
        this.processing_side = processing_side;
    }

    public String getProduceAmount() {
        return produceAmount;
    }

    public void setProduceAmount(String produceAmount) {
        this.produceAmount = produceAmount;
    }

    public String getPurchase_director() {
        return purchase_director;
    }

    public void setPurchase_director(String purchase_director) {
        this.purchase_director = purchase_director;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
