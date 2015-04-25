package nju.zhizaolian.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ColorfulCode on 2015/4/23.
 */
public class ListInfo implements Serializable{
    private Order order;
    private String taskTime;
    private Employee employee;
    private String orderId;

    public static ListInfo fromJson(JSONObject jsonObject){
        if (jsonObject==null){
            return  null;
        }
        ListInfo listInfo = null;
        try {
            listInfo=new ListInfo();
            listInfo.order=Order.fromJson(jsonObject.getJSONObject("order"));
            listInfo.employee=Employee.fromJson(jsonObject.getJSONObject("employee"));
            listInfo.orderId=jsonObject.getString("orderId");
            listInfo.taskTime=jsonObject.getString("taskTime");
            return  listInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listInfo;
    }

    public static ArrayList<ListInfo> fromJson(JSONArray array){
        ArrayList<ListInfo> listInfos=new ArrayList<>();
        for (int i=0;i<array.length();i++){
            JSONObject object = null;
            try {
                object=array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListInfo info = fromJson(object);
            if(info!=null){
                listInfos.add(info);
            }
        }
        return listInfos;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


}
