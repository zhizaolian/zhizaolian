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
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {

    private ListView orderList;
    OrderProcessListAdapter adapter;
    ArrayList<HashMap<String,String>> dataList;
    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_process_list,container,false);
        orderList =(ListView) view.findViewById(R.id.order_process_list);
        dataList = new ArrayList<>();
        adapter = new OrderProcessListAdapter(this.getActivity(),dataList,R.layout.order_process_list_item,
                                                new String[]{"name","number","date","state","s_name","c_name","cc_name"},
                                                new int[]{R.id.order_process_list_item_style_name,
                                                           R.id.order_process_list_item_order_number,
                                                           R.id.order_process_list_item_order_start_date,
                                                           R.id.order_process_list_item_order_state,
                                                           R.id.order_process_list_item_order_salesman_name,
                                                           R.id.order_process_list_item_customer_name,
                                                           R.id.order_process_list_item_custom_company_name}
                                                );
        orderList.setAdapter(adapter);
        return view;
    }

    public void updateListView(ArrayList<ListInfo> orderListInfo){
        dataList.clear();
        for(int i=0;i<orderListInfo.size();i++) {
            HashMap<String, String> data = new HashMap<>();
            data.put("name", orderListInfo.get(i).getOrder().getStyleName());
            data.put("number",""+orderListInfo.get(i).getOrderId());
            data.put("date", orderListInfo.get(i).getOrder().getOrderTime());
            data.put("state", orderListInfo.get(i).getOrder().getOrderProcessStateName());
            data.put("s_name", orderListInfo.get(i).getEmployee().getEmployeeName());
            data.put("c_name", orderListInfo.get(i).getOrder().getCustomerName());
            data.put("cc_name", orderListInfo.get(i).getOrder().getCustomerCompany());
            data.put("image_url",orderListInfo.get(i).getOrder().getSampleClothesThumbnailPicture());
            data.put("big_image_url",orderListInfo.get(i).getOrder().getSampleClothesPicture());
            dataList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

}
