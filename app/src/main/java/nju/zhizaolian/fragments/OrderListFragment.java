package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.OrderProcessListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {

    private ListView orderList;
    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_process_list,container,false);
        orderList =(ListView) view.findViewById(R.id.order_process_list);
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

        //Test Data
        for(int i=0;i<2;i++) {
            HashMap<String, String> data = new HashMap<>();
            data.put("name", "裙摆");
            data.put("number", "2103013012013012031");
            data.put("date", "2014-08-02 13:24:24");
            data.put("state", "完工");
            data.put("s_name", "市场专员12138");
            data.put("c_name", "吕柯大爷");
            data.put("cc_name", "南京大学软件学院");
            dataList.add(data);
        }
        OrderProcessListAdapter adapter = new OrderProcessListAdapter(this.getActivity(),dataList,R.layout.order_process_list_item,
                                                new String[]{"name","number","date","state","s_name","c_name","cc_name"},
                                                new int[]{R.id.order_process_list_item_style_name,
                                                           R.id.order_process_list_item_order_number,
                                                           R.id.order_process_list_item_order_start_date,
                                                           R.id.order_process_list_item_order_state,
                                                           R.id.order_process_list_item_order_salesman_name,
                                                           R.id.order_process_list_item_customer_name,
                                                           R.id.order_process_list_item_custom_company_name},
                                                new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher});
        orderList.setAdapter(adapter);
        return view;
    }


}
