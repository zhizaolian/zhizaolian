package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Employee;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 * 基本信息
 */
public class OrderBaseInfoShowFragment extends Fragment {
    private OrderInfo orderInfo;
    private Order order;
    private Employee employee;
    private TextView orderNumberView;
    private TextView orderTimeView;
    private TextView orderSalesmanView;
    private TextView orderSourceView;
    private TextView ifDuplicateOrderView;
    private TextView customerId;
    private TextView customerName;
    private TextView company;
    private TextView companyFax;
    private TextView customerPhone1;
    private TextView customerPhone2;
    private TextView companyAddress;

    private TextView styleNameView;
    private TextView styleSexView;
    private TextView styleSeasonView;
    private TextView fabricTypeView;
    private TextView specialTechnologyView;
    private TextView otherExplainView;
    private TextView referenceUrlView;
    private TextView salesmanEmailView;
    private TextView salesmanPhoneView;
    private TextView salesmanOfficeView;
    private TextView salesmanQQView;


    public OrderBaseInfoShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderInfo= (OrderInfo) getArguments().getSerializable("info");
        order=orderInfo.getOrder();
        employee=orderInfo.getEmployee();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_base_info_show, container, false);
        orderNumberView=(TextView)view.findViewById(R.id.business_number_view);
        orderTimeView=(TextView)view.findViewById(R.id.order_time_view);
        orderSalesmanView=(TextView)view.findViewById(R.id.order_salesman_view);
        orderSourceView=(TextView)view.findViewById(R.id.order_source_view);
        ifDuplicateOrderView=(TextView)view.findViewById(R.id.if_duplicate_order_view);

        customerId=(TextView)view.findViewById(R.id.custom_id);
        customerName=(TextView)view.findViewById(R.id.custom_name);
        company=(TextView)view.findViewById(R.id.custom_company);
        companyFax=(TextView)view.findViewById(R.id.company_fax);
        customerPhone1=(TextView)view.findViewById(R.id.customer_phone1);
        customerPhone2=(TextView)view.findViewById(R.id.customer_phone2);
        companyAddress=(TextView)view.findViewById(R.id.company_address);

         styleNameView=(TextView)view.findViewById(R.id.style_name_view);
         styleSexView=(TextView)view.findViewById(R.id.style_sex_view);
         styleSeasonView=(TextView)view.findViewById(R.id.style_season_view);
         fabricTypeView=(TextView)view.findViewById(R.id.fabric_type_view);
        specialTechnologyView=(TextView)view.findViewById(R.id.special_technology_view);
         otherExplainView=(TextView)view.findViewById(R.id.other_explain_view);
         referenceUrlView=(TextView)view.findViewById(R.id.reference_url_view);
         salesmanEmailView=(TextView)view.findViewById(R.id.salesman_email_view);
         salesmanPhoneView=(TextView)view.findViewById(R.id.salesman_phone_view);
         salesmanOfficeView=(TextView)view.findViewById(R.id.salesman_office_phone_view);
         salesmanQQView=(TextView)view.findViewById(R.id.salesman_qq_view);

        setUpOrderBaseInfo();
        return view;
    }

    public void setUpOrderBaseInfo(){
        orderNumberView.setText(orderInfo.getOrderId());
        orderTimeView.setText(order.getOrderTime());
        orderSalesmanView.setText(employee.getEmployeeName());
        orderSourceView.setText(order.getOrderSource());
        if(Integer.parseInt(order.getReorder()) == 0){
            ifDuplicateOrderView.setText("否");
        }else {
            ifDuplicateOrderView.setText("是");
        }


        customerId.setText(order.getCustomerId());
        customerName.setText(order.getCustomerName());
        company.setText(order.getCustomerCompany());
        companyFax.setText(order.getCustomerCompanyFax());
        customerPhone1.setText(order.getCustomerPhone1());
        customerPhone2.setText(order.getCustomerPhone2());
        companyAddress.setText(order.getCustomerCompanyAddress());

        styleNameView.setText(order.getStyleName());
        styleSexView.setText(order.getStyleSex());
        styleSeasonView.setText(order.getStyleSeason());
        fabricTypeView.setText(order.getFabricType());
        specialTechnologyView.setText(order.getSpecialProcess());
        otherExplainView.setText(order.getOtherRequirements());
        referenceUrlView.setText(order.getReferenceUrl());
        salesmanEmailView.setText(employee.getEmail());
        salesmanPhoneView.setText(employee.getPhone1());
        salesmanOfficeView.setText(employee.getJobPhone());
        salesmanQQView.setText(employee.getQq());
    }


}
