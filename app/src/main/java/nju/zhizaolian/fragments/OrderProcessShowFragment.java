package nju.zhizaolian.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.ProduceAdapter;
import nju.zhizaolian.adapters.RepairRecordAdapter;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;


public class OrderProcessShowFragment extends Fragment {


    private OrderInfo orderInfo;
    private Order order;


    private TextView sampleTotalNumberView;
    private TextView goodsTotalNumberView;
    private TextView deliverDateView;
    private TextView deliverPeriodView;
    private ListView sampleProduceListView;
    private ListView goodsProduceListView;
    private ListView repairRecordListView;
    private TextView processNameView;


    private ProduceAdapter sampleAdapter;
    private ProduceAdapter goodsAdapter;
    private RepairRecordAdapter repairRecordAdapter;

    public static OrderProcessShowFragment newInstance(Bundle bundle ) {
        OrderProcessShowFragment fragment = new OrderProcessShowFragment();
        Bundle args = bundle;

        fragment.setArguments(args);
        return fragment;
    }

    public OrderProcessShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            orderInfo= (OrderInfo) getArguments().getSerializable("info");
            order=orderInfo.getOrder();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_process_show, container, false);
        sampleTotalNumberView=(TextView)view.findViewById(R.id.sample_total_number_view);
         goodsTotalNumberView=(TextView)view.findViewById(R.id.goods_total_number_view);
         deliverDateView=(TextView)view.findViewById(R.id.ask_deliver_date_view);
         deliverPeriodView=(TextView)view.findViewById(R.id.ask_produce_period_view);
         sampleProduceListView=(ListView)view.findViewById(R.id.sample_process_list_view);
         goodsProduceListView=(ListView)view.findViewById(R.id.goods_process_list_view);
         repairRecordListView=(ListView)view.findViewById(R.id.repair_record_list_view);
         processNameView=(TextView)view.findViewById(R.id.process_name_view);
            init();
        return view;
    }


    public  void init(){
        sampleTotalNumberView.setText(order.getSampleAmount());
        goodsTotalNumberView.setText(order.getAskAmount());
        deliverDateView.setText(order.getAskDeliverDate());
        deliverPeriodView.setText(order.getAskProducePeriod());
        if(Integer.parseInt(order.getReorder()) == 0){
            sampleAdapter=new ProduceAdapter(getActivity(),0,orderInfo.getSample());
            sampleProduceListView.setAdapter(sampleAdapter);
        }
        goodsAdapter=new ProduceAdapter(getActivity(),0,orderInfo.getProduce());
        goodsProduceListView.setAdapter(goodsAdapter);
        processNameView.setText(order.getPayAccountInfo());
        repairRecordAdapter=new RepairRecordAdapter(getActivity(),0, orderInfo.getRepairRecords());
        repairRecordListView.setAdapter(repairRecordAdapter);


    }


}
