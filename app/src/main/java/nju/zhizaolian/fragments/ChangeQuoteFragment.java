package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.AccessoriesCostAdapter;
import nju.zhizaolian.adapters.FabricCostAdapter;
import nju.zhizaolian.models.AccessoryCost;
import nju.zhizaolian.models.Craft;
import nju.zhizaolian.models.FabricCost;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;
import nju.zhizaolian.models.Quote;

/**
 *  修改报价
 */
public class ChangeQuoteFragment extends Fragment {
    private static String changeQuoteDetailUrl="/fmc/market/mobile_modifyQuoteDetail.do";
    private static String changeQuoteSubmitUrl="/fmc/market/mobile_mobile_modifyQuoteSubmit.do";

    private EditText profitPerClothesEdit;
    private Button changeQuoteButton;
    private TextView customQuoteView;
    private TextView singleCostView;
    private TextView innerCostView;

    private ListView fabricCostListView;
    private ListView accessoriesCostListView;
    private TextView fabricTotalCostView;
    private TextView accessoriesTotalCostView;

    private TextView stampDutyMoneyView;
    private TextView washHangDyeMoneyView;
    private TextView laserMoneyView;
    private TextView embroideryMoneyView;
    private TextView crumpleMoneyView;
    private TextView openVersionMoneyView;

    private TextView cutCostView;
    private TextView manageCostView;
    private TextView swingCostView;
    private TextView ironingCostView;
    private TextView nailCostView;
    private TextView packageCostView;
    private TextView otherCostView;
    private TextView designCostView;



    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;
    private FabricCostAdapter fabricCostAdapter;
    private AccessoriesCostAdapter accessoriesCostAdapter;


    private ProgressDialog progressDialog;



    public ChangeQuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_change_quote, container, false);

        listInfo= (ListInfo) getArguments().getSerializable("info");

        singleCostView=(TextView)view.findViewById(R.id.total_cost_view);
        innerCostView=(TextView)view.findViewById(R.id.produce_price_view);

        profitPerClothesEdit=(EditText)view.findViewById(R.id.profit_per_clothes_edit);
        changeQuoteButton=(Button)view.findViewById(R.id.change_quote_button);
        customQuoteView=(TextView)view.findViewById(R.id.custom_quote_view);
        cutCostView=(TextView)view.findViewById(R.id.cutCostView);
        manageCostView=(TextView)view.findViewById(R.id.manageCostView);
        swingCostView=(TextView)view.findViewById(R.id.swingCostView);
        ironingCostView=(TextView)view.findViewById(R.id.ironingCostView);
        nailCostView=(TextView)view.findViewById(R.id.nailCostView);
        packageCostView=(TextView)view.findViewById(R.id.packageCostView);
        otherCostView=(TextView)view.findViewById(R.id.otherCostView);
        designCostView=(TextView)view.findViewById(R.id.degisnCostView);
        fabricCostListView=(ListView)view.findViewById(R.id.change_quote_fabric_cost_list_view);
        accessoriesCostListView=(ListView)view.findViewById(R.id.change_quote_accessories_cost_list_view);
        fabricTotalCostView=(TextView)view.findViewById(R.id.fabric_total_cost_view);
        accessoriesTotalCostView=(TextView)view.findViewById(R.id.accessories_total_cost_view);

        stampDutyMoneyView=(TextView)view.findViewById(R.id.stamp_duty_money_view);
        washHangDyeMoneyView=(TextView)view.findViewById(R.id.wash_hang_dye_money_view);
        laserMoneyView=(TextView)view.findViewById(R.id.laser_money_view);
        embroideryMoneyView=(TextView)view.findViewById(R.id.embroidery_money_view);
        crumpleMoneyView=(TextView)view.findViewById(R.id.crumple_money_view);
        openVersionMoneyView=(TextView)view.findViewById(R.id.open_version_money_view);

        progressDialog=ProgressDialog.show(container.getContext(),"请等待","下载数据中...",true);
        getChangeQuoteDetail();


        profitPerClothesEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    customQuoteView.setText("0");
                }else {
                    Float perProfit= Float.valueOf(s.toString());
                    Float innerPrice= Float.valueOf(innerCostView.getText().toString());
                    Float outerPrice=perProfit+innerPrice;
                    customQuoteView.setText(outerPrice.toString());
                }

            }
        });
        changeQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定操作?");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog=ProgressDialog.show(getActivity(),"请等待","提交中",true);
                        changeQuoteSubmit();
                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });
        return view;
    }


    public void getChangeQuoteDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId", listInfo.getOrder().getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP()+changeQuoteDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success", response.toString());
                try {
                    orderInfo= OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    Quote quote=orderInfo.getQuote();
                    Craft craft=orderInfo.getCraft();
                    profitPerClothesEdit.setText(orderInfo.getQuote().getProfitPerPiece());
                    singleCostView.setText(quote.getSingleCost().toString());
                    innerCostView.setText(quote.getInnerPrice().toString());
                    customQuoteView.setText(orderInfo.getQuote().getOuterPrice());
                    ArrayList<FabricCost> fabricCostArrayList=orderInfo.getFabricCosts();
                    ArrayList<AccessoryCost> accessoryCostArrayList=orderInfo.getAccessoryCosts();
                    fabricCostAdapter=new FabricCostAdapter(getActivity(),0,fabricCostArrayList);
                    fabricCostListView.setAdapter(fabricCostAdapter);
                    accessoriesCostAdapter=new AccessoriesCostAdapter(getActivity(),0,accessoryCostArrayList);
                    accessoriesCostListView.setAdapter(accessoriesCostAdapter);
                    fabricTotalCostView.setText(quote.getFabricCost().toString());
                    accessoriesTotalCostView.setText(quote.getAccessoryCost().toString());
                    cutCostView.setText(quote.getCutCost().toString());
                    manageCostView.setText(quote.getManageCost().toString());
                    swingCostView.setText(quote.getSwingCost().toString());
                    ironingCostView.setText((quote.getIroningCost().toString()));
                    nailCostView.setText(quote.getNailCost().toString());
                    packageCostView.setText(quote.getPackageCost().toString());
                    otherCostView.setText(quote.getOtherCost().toString());
                    designCostView.setText(quote.getDesignCost().toString());
                    stampDutyMoneyView.setText(String.valueOf(craft.getStampDutyMoney()));
                    washHangDyeMoneyView.setText(String.valueOf(craft.getWashHangDyeMoney()));
                    laserMoneyView.setText(String.valueOf(craft.getLaserMoney()));
                    embroideryMoneyView.setText(String.valueOf(craft.getEmbroideryMoney()));
                    crumpleMoneyView.setText(String.valueOf(craft.getCrumpleMoney()));
                    openVersionMoneyView.setText(String.valueOf(craft.getOpenVersionMoney()));




                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        });




    }


    public void changeQuoteSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("profitPerPiece",profitPerClothesEdit.getText().toString());
        params.put("inner_price",innerCostView.getText().toString());
        params.put("outer_price",customQuoteView.getText().toString());
        params.put("single_cost",singleCostView.getText().toString());
        params.put("order_id",order.getOrderId());
        params.put("taskId", orderInfo.getTaskId());
        params.put("processId",orderInfo.getProcessInstanceId());

        client.post(IPAddress.getIP()+changeQuoteSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });
    }


}
