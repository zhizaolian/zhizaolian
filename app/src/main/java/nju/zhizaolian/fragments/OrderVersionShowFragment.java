package nju.zhizaolian.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.VersionAdapter;
import nju.zhizaolian.models.DesignCad;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;


public class OrderVersionShowFragment extends Fragment {

    private OrderInfo orderInfo;
    private Order order;


    private ListView versionListView;
    private TextView cadFabricView;
    private TextView cadBoxView;
    private TextView cadVersionView;
    private TextView cadPackageView;
    private TextView cadTechView;
    private TextView cadOtherView;

    private VersionAdapter versionAdapter;

    public static OrderVersionShowFragment newInstance(Bundle bundle) {
        OrderVersionShowFragment fragment = new OrderVersionShowFragment();
        Bundle args = bundle;

        fragment.setArguments(args);
        return fragment;
    }

    public OrderVersionShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderInfo= (OrderInfo) getArguments().getSerializable("info");
            order=orderInfo.getOrder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_version_show, container, false);
        versionListView=(ListView)view.findViewById(R.id.version_list_view);
        cadFabricView=(TextView)view.findViewById(R.id.cad_fabric_view);
        cadBoxView=(TextView)view.findViewById(R.id.cad_cadBox_view);
        cadVersionView=(TextView)view.findViewById(R.id.cad_version_data_view);
        cadPackageView=(TextView)view.findViewById(R.id.cad_package_view);
        cadTechView=(TextView)view.findViewById(R.id.cad_tech_view);
        cadOtherView=(TextView)view.findViewById(R.id.cad_other_view);
        init();

        return view;
    }
    public void init(){
        DesignCad designCad=orderInfo.getDesignCad();
        cadFabricView.setText(designCad.getCadFabric());
        cadBoxView.setText(designCad.getCadBox());
        cadVersionView.setText(designCad.getCadVersionData());
        cadPackageView.setText(designCad.getCadPackage());
        cadTechView.setText(designCad.getCadTech());
        cadOtherView.setText(designCad.getCadOther());

        versionAdapter=new VersionAdapter(getActivity(),0,orderInfo.getVersionDatas());
        versionListView.setAdapter(versionAdapter);

    }



}
