package nju.zhizaolian.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentDesignActivity;
import nju.zhizaolian.activities.DepartmentFinancialActivity;
import nju.zhizaolian.activities.DepartmentLogisticActivity;
import nju.zhizaolian.activities.DepartmentProductionActivity;
import nju.zhizaolian.activities.DepartmentPurchaseActivity;
import nju.zhizaolian.activities.DepartmentQualityActivity;
import nju.zhizaolian.activities.DepartmentSalesActivity;
import nju.zhizaolian.activities.DepartmentSecretaryActivity;
import nju.zhizaolian.activities.DepartmentSweaterMakerActivity;
import nju.zhizaolian.activities.DepartmentTechnologyActivity;
import nju.zhizaolian.activities.MainActivity;
import nju.zhizaolian.activities.SalesMasterActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.TaskNumber;


/**
 * Created by ColorfulCode on 2015/4/20.
 */
public class MainMenuAdapter extends SimpleAdapter{
    Context context;
    List<? extends Map<String, ?>> data;
    int resource;
    String[] from;
    int[] to;
    Account account;
    Activity activity;
    TaskNumber taskNumber;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(resource,parent,false);
        for(int i=0;i<from.length;i++){
            ((TextView) view.findViewById(to[i])).setText((String)data.get(position).get(from[i]));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String department =((TextView)v.findViewById(R.id.item_department)).getText().toString();
                Bundle bundle = new Bundle();
                bundle.putSerializable("account",account);
                bundle.putSerializable("taskNumber",taskNumber);
                Intent intent = new Intent(activity, MainActivity.class);
                switch (department){
                    case "市场主管":
                        intent=new Intent(activity, SalesMasterActivity.class);
                        break;
                    case "市场部":
                        intent=new Intent(activity, DepartmentSalesActivity.class);
                        break;
                    case "秘书部":
                        intent=new Intent(activity, DepartmentSecretaryActivity.class);
                        break;
                    case "设计部":
                        intent=new Intent(activity, DepartmentDesignActivity.class);
                        break;
                    case "工艺部":
                        intent=new Intent(activity, DepartmentTechnologyActivity.class);
                        break;
                    case "采购部":
                        intent=new Intent(activity, DepartmentPurchaseActivity.class);
                        break;
                    case "生产部":
                        intent=new Intent(activity, DepartmentProductionActivity.class);
                        break;
                    case "毛衣制作部":
                        intent=new Intent(activity, DepartmentSweaterMakerActivity.class);
                        break;
                    case "财务部":
                        intent=new Intent(activity, DepartmentFinancialActivity.class);
                        break;
                    case "物流部":
                        intent=new Intent(activity, DepartmentLogisticActivity.class);
                        break;
                    case "质检部":
                        intent=new Intent(activity, DepartmentQualityActivity.class);
                        break;
                }
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        return view;
    }

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public MainMenuAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,Account account,Activity activity,TaskNumber taskNumber) {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=data;
        this.resource=resource;
        this.from=from;
        this.to=to;
        this.account=account;
        this.activity=activity;
        this.taskNumber=taskNumber;
    }
}
