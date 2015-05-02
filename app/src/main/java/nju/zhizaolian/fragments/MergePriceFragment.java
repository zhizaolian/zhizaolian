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
import nju.zhizaolian.models.FabricCost;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;
import nju.zhizaolian.models.Quote;


public class MergePriceFragment extends Fragment {

    private  EditText profitPerClothesEdit;
    private  Button mergePriceEnsureButton;
    private  Button mergePriceCancelButton;
    private ListView fabricCostListView;
    private ListView accessoriesCostListView;
    private TextView fabricTotalCostView;
    private TextView accessoriesTotalCostView;
    private TextView cutCostView;
    private TextView manageCostView;
    private TextView swingCostView;
    private TextView ironingCostView;
    private TextView nailCostView;
    private TextView packageCostView;
    private TextView otherCostView;
    private TextView designCostView;




    private TextView totalCostView;
    private TextView produceQuoteView;
    private TextView customQuoteView;
    private FabricCostAdapter fabricCostAdapter;
    private AccessoriesCostAdapter accessoriesCostAdapter;


    private  Order order;
    private  ListInfo listInfo;
    private  OrderInfo orderInfo;
    private Quote quote;
   private String mergeQuoteDetailUrl="/fmc/market/mobile_mergeQuoteDetail.do";
   private String mergeQuoteSubmitUrl="/fmc/market/mobile_mergeQuoteSubmit.do";

    private ProgressDialog progressDialog;

    public MergePriceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_merge_price, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        fabricCostListView=(ListView)view.findViewById(R.id.fabric_cost_list_view);
        accessoriesCostListView=(ListView)view.findViewById(R.id.accessories_cost_list_view);
        fabricTotalCostView=(TextView)view.findViewById(R.id.fabric_total_cost_view);
        accessoriesTotalCostView=(TextView)view.findViewById(R.id.accessories_total_cost_view);
         cutCostView=(TextView)view.findViewById(R.id.cutCostView);
         manageCostView=(TextView)view.findViewById(R.id.manageCostView);
         swingCostView=(TextView)view.findViewById(R.id.swingCostView);
         ironingCostView=(TextView)view.findViewById(R.id.ironingCostView);
         nailCostView=(TextView)view.findViewById(R.id.nailCostView);
         packageCostView=(TextView)view.findViewById(R.id.packageCostView);
         otherCostView=(TextView)view.findViewById(R.id.otherCostView);
         designCostView=(TextView)view.findViewById(R.id.degisnCostView);

        profitPerClothesEdit=(EditText)view.findViewById(R.id.profit_per_clothes_edit);
        mergePriceEnsureButton=(Button)view.findViewById(R.id.merge_price_ensure_button);
        mergePriceCancelButton=(Button)view.findViewById(R.id.merge_price_cancel_button);
         totalCostView=(TextView)view.findViewById(R.id.total_cost_view);
         produceQuoteView=(TextView)view.findViewById(R.id.produce_price_view);
         customQuoteView=(TextView)view.findViewById(R.id.custom_quote_view);


        progressDialog=ProgressDialog.show(container.getContext(),"请等待","正在获取数据",true);
        getMergePriceDetail();

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
                    Float innerPrice= Float.valueOf(produceQuoteView.getText().toString());
                    Float outerPrice=perProfit+innerPrice;
                    customQuoteView.setText(outerPrice.toString());
                }


            }
        });

        mergePriceEnsureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profitPerClothesEdit.getText().length()==0){
                    Toast.makeText(getActivity(), "请填写信息", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(container.getContext());
                    builder.setTitle("提示");
                    builder.setMessage("确定操作?");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mergePriceSubmit();
                            progressDialog=ProgressDialog.show(container.getContext(),"请等待","正在上传",true);
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
            }
        });

        mergePriceCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "暂不可用", Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }

    public void mergePriceSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("profitPerPiece",profitPerClothesEdit.getText().toString());
        params.put("inner_price",produceQuoteView.getText().toString());
        params.put("outer_price",customQuoteView.getText().toString());
        params.put("single_cost",totalCostView.getText().toString());
        params.put("order_id",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("processId",orderInfo.getProcessInstanceId());
        client.post(IPAddress.getIP()+mergeQuoteSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String isSuccess=response.getString("isSuccess");
                    if (isSuccess.equals("true")){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();

                        getActivity().finish();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "出现错误", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        });


    }
    public void getMergePriceDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",listInfo.getOrder().getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP()+mergeQuoteDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success",response.toString());
                try {
                    orderInfo= OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    quote=orderInfo.getQuote();

                    totalCostView.setText(quote.getSingleCost().toString());
                    produceQuoteView.setText(quote.getSingleCost().toString());
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
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

            }
        });
    }

}
