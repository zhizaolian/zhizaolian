package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/4/27.
 */
public class AccessoryCost {

    private Integer id;
    private Integer orderId;
    private String accessoryName;
    private Float tearPerPiece;
    private Float price;
    private Float costPerPiece;
    private Float tearPerPieceSampleAmountProduct;
    private Float tearPerPieceAskAmountProduct;

    // Constructors
    /** default constructor */
    public AccessoryCost() {
    }

    /** full constructor */
    public AccessoryCost(Integer orderId, String accessoryName,
                         Float tearPerPiece, Float price, Float costPerPiece) {
        this.orderId = orderId;
        this.accessoryName = accessoryName;
        this.tearPerPiece = tearPerPiece;
        this.price = price;
        this.costPerPiece = costPerPiece;
    }


    public static AccessoryCost fromJson(JSONObject jsonObject){
        AccessoryCost accessoryCost=new AccessoryCost();
        try {
            accessoryCost.accessoryName=jsonObject.getString("accessoryName");
            accessoryCost.costPerPiece= Float.valueOf(jsonObject.getString("costPerPiece"));
            accessoryCost.id= Integer.valueOf(jsonObject.getString("id"));
            accessoryCost.orderId= Integer.valueOf(jsonObject.getString("orderId"));
            accessoryCost.price= Float.valueOf(jsonObject.getString("price"));
            accessoryCost.tearPerPiece= Float.valueOf(jsonObject.getString("tearPerPiece"));
            accessoryCost.tearPerPieceAskAmountProduct= Float.valueOf(jsonObject.getString("tearPerPieceAskAmountProduct"));
            accessoryCost.tearPerPieceSampleAmountProduct= Float.valueOf(jsonObject.getString("tearPerPieceSampleAmountProduct"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accessoryCost;
    }

    public static ArrayList<AccessoryCost> fromJson(JSONArray jsonArray){
        ArrayList<AccessoryCost> accessoryCostArrayList=new ArrayList<AccessoryCost>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
           AccessoryCost accessoryCost=AccessoryCost.fromJson(jsonObject);
            if(accessoryCost != null){
                accessoryCostArrayList.add(accessoryCost);
            }
        }

        return accessoryCostArrayList;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public Float getTearPerPiece() {
        return tearPerPiece;
    }

    public void setTearPerPiece(Float tearPerPiece) {
        this.tearPerPiece = tearPerPiece;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getCostPerPiece() {
        return costPerPiece;
    }

    public void setCostPerPiece(Float costPerPiece) {
        this.costPerPiece = costPerPiece;
    }

    public Float getTearPerPieceSampleAmountProduct() {
        return tearPerPieceSampleAmountProduct;
    }

    public void setTearPerPieceSampleAmountProduct(Float tearPerPieceSampleAmountProduct) {
        this.tearPerPieceSampleAmountProduct = tearPerPieceSampleAmountProduct;
    }

    public Float getTearPerPieceAskAmountProduct() {
        return tearPerPieceAskAmountProduct;
    }

    public void setTearPerPieceAskAmountProduct(Float tearPerPieceAskAmountProduct) {
        this.tearPerPieceAskAmountProduct = tearPerPieceAskAmountProduct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
