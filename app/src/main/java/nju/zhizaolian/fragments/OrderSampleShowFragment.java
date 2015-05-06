package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Employee;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.Logistics;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 * 样衣信息界面
 */
public class OrderSampleShowFragment extends Fragment {
    private OrderInfo orderInfo;
    private Order order;
    private Employee employee;
    private Logistics logistics;

    private TextView ifProvideSampleView;
    private TextView expressTimeView;
    private TextView expressNameView;
    private TextView expressNumberView;
    private TextView ifMakeSampleView;
    private TextView postManView;
    private TextView postManPhoneView;
    private TextView postManAddressView;
    private TextView produceExpressTimeView;
    private TextView produceExpressNameView;
    private TextView produceExpressNumberView;
    private TextView remarkInfoView;
    private ImageView samplePictureImage;
    private ImageView referencePictureImage;

    public OrderSampleShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderInfo= (OrderInfo) getArguments().getSerializable("info");
        order=orderInfo.getOrder();
        employee=orderInfo.getEmployee();
        logistics=orderInfo.getLogistics();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_sample_show, container, false);
        ifProvideSampleView=(TextView)view.findViewById(R.id.is_Provide_Sample_view);
         expressTimeView=(TextView)view.findViewById(R.id.express_time_view);
         expressNameView=(TextView)view.findViewById(R.id.express_name_view);
        expressNumberView=(TextView)view.findViewById(R.id.express_number_view);
         ifMakeSampleView=(TextView)view.findViewById(R.id.if_make_sample_view);
         postManView=(TextView)view.findViewById(R.id.post_man_view);
         postManPhoneView=(TextView)view.findViewById(R.id.post_man_phone_view);
         postManAddressView=(TextView)view.findViewById(R.id.post_address_view);
         produceExpressTimeView=(TextView)view.findViewById(R.id.produce_express_time_view);
         produceExpressNameView=(TextView)view.findViewById(R.id.produce_express_name_view);
        produceExpressNumberView=(TextView)view.findViewById(R.id.produce_express_number_view);
        remarkInfoView=(TextView)view.findViewById(R.id.remark_info_view);
        samplePictureImage=(ImageView)view.findViewById(R.id.sample_picture_image);
        referencePictureImage=(ImageView)view.findViewById(R.id.reference_picture_image);

        init();
        return view;
    }

    public void init(){
        if (Integer.parseInt(order.getHasPostedSampleClothes() )== 0){
            ifProvideSampleView.setText("没有样衣");
        }else if (Integer.parseInt(order.getHasPostedSampleClothes() )== 1){
            ifProvideSampleView.setText("未收到样衣");
        }else if (Integer.parseInt(order.getHasPostedSampleClothes() )== 2){
            ifProvideSampleView.setText("收到样衣");
        }
         expressTimeView.setText(logistics.getInPostSampleClothesTime());
         expressNameView.setText(logistics.getInPostSampleClothesType());
         expressNumberView.setText(logistics.getInPostSampleClothesNumber());
        if (Integer.parseInt(order.getIsNeedSampleClothes()) == 0){
            ifMakeSampleView.setText("否");
        }else {
            ifMakeSampleView.setText("是");
        }

         postManView.setText(logistics.getSampleClothesName());
         postManPhoneView.setText(logistics.getSampleClothesPhone());
         postManAddressView.setText(logistics.getSampleClothesAddress());
         produceExpressTimeView.setText(logistics.getSampleClothesTime());
         produceExpressNameView.setText(logistics.getSampleClothesType());
         produceExpressNumberView.setText(logistics.getSampleClothesNumber());
         remarkInfoView.setText(logistics.getSampleClothesRemark());
         Picasso.with(getActivity()).load(IPAddress.getIP()+order.getSampleClothesPicture()).into(samplePictureImage);
         Picasso.with(getActivity()).load(IPAddress.getIP()+order.getReferencePicture()).into(referencePictureImage);


    }
}
