package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lk on 15/5/2.
 */
public class PackageDetail {
    private Integer id;
    private Integer packageId;
    private String clothesStyleName;
    private String clothesStyleColor;
    private Integer clothesAmount;

    // Constructors

    /** default constructor */
    public PackageDetail() {
    }

    /** full constructor */
    public PackageDetail(Integer packageId, String clothesStyleName,
                         String clothesStyleColor, Integer clothesAmount) {
        this.packageId = packageId;
        this.clothesStyleName = clothesStyleName;
        this.clothesStyleColor = clothesStyleColor;
        this.clothesAmount = clothesAmount;
    }
    public static PackageDetail fromJson(JSONObject jsonObject){
        PackageDetail packageDetail=new PackageDetail();
        try {
            packageDetail.id= Integer.valueOf(jsonObject.getString("id"));
            packageDetail.packageId= Integer.valueOf(jsonObject.getString("packageId"));
            packageDetail.clothesAmount= Integer.valueOf(jsonObject.getString("clothesAmount"));
            packageDetail.clothesStyleName=jsonObject.getString("clothesStyleName");
            packageDetail.clothesStyleColor=jsonObject.getString("clothesStyleColor");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return packageDetail;
    }
    public static ArrayList<PackageDetail>  fromJson(JSONArray jsonArray){
        ArrayList<PackageDetail> packageDetailArrayList=new ArrayList<PackageDetail>(jsonArray.length());
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                jsonObject= jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            PackageDetail packageDetail=PackageDetail.fromJson(jsonObject);
            if(packageDetail != null){
                packageDetailArrayList.add(packageDetail);
            }
        }
        return  packageDetailArrayList;


    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getClothesStyleName() {
        return clothesStyleName;
    }

    public void setClothesStyleName(String clothesStyleName) {
        this.clothesStyleName = clothesStyleName;
    }

    public String getClothesStyleColor() {
        return clothesStyleColor;
    }

    public void setClothesStyleColor(String clothesStyleColor) {
        this.clothesStyleColor = clothesStyleColor;
    }

    public Integer getClothesAmount() {
        return clothesAmount;
    }

    public void setClothesAmount(Integer clothesAmount) {
        this.clothesAmount = clothesAmount;
    }
}
