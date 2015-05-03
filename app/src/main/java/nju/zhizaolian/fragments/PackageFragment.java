package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.PackageAdapter;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;
import nju.zhizaolian.models.PackageDetail;
import nju.zhizaolian.models.Produce;

/**
 *
 */
public class PackageFragment extends Fragment  {
    private static String packageDetailUrl="/fmc/logistics/mobile_packageDetail.do";
    private static String packageAddPackageUrl="/fmc/logistics/mobile_addPackage.do";
    private static String packageRemovePackageUrl="/fmc/logistics/mobile_removePackage.do";
    private static String packageSubmitUrl="/fmc/logistics/mobile_packageSubmit.do";

    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;
    private ArrayList<PackageDetail> packageDetailArrayList=new ArrayList<PackageDetail>();
    private ArrayList<PackageDetail> totalPackageDetailArrayList=new ArrayList<PackageDetail>();
    private PackageAdapter packageAdapter;
    private PackageAdapter totalPackageAdapter;


    private ProgressDialog progressDialog;
    private Spinner packageColorSpinner;
    private Spinner packageSizeSpinner;
    private EditText packageNumberEdit;
    private Button packageAddButton;
    private ListView packageItemListView;
    private Button packageAddPackageButton;
    private Button packageSubmitButton;
    private ListView packageDetailListView;


    public PackageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_warehouse_entry, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        packageAdapter=new PackageAdapter(getActivity(),0,packageDetailArrayList);


        packageColorSpinner=(Spinner)view.findViewById(R.id.package_color_spinner);
        packageSizeSpinner=(Spinner)view.findViewById(R.id.package_clothes_size_spinner);
        packageNumberEdit=(EditText)view.findViewById(R.id.package_clothes_number_edit);
        packageAddButton=(Button)view.findViewById(R.id.package_add_button);
        packageItemListView=(ListView)view.findViewById(R.id.package_item_list_view);
        packageAddPackageButton=(Button)view.findViewById(R.id.package_add_package_number_button);
        packageSubmitButton=(Button)view.findViewById(R.id.package_submit_button);
        packageDetailListView=(ListView)view.findViewById(R.id.package_detail_list_view);
        packageItemListView.setAdapter(packageAdapter);


        progressDialog=ProgressDialog.show(getActivity(),"请稍等","数据下载中",true,true);
        getPackageDetail();

        packageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(packageNumberEdit.getText().length() == 0){
                    Toast.makeText(getActivity(),"请填写数据",Toast.LENGTH_SHORT).show();
                }else {
                    String color=packageColorSpinner.getSelectedItem().toString();
                    String size=packageSizeSpinner.getSelectedItem().toString();
                    String number=packageNumberEdit.getText().toString();
                    PackageDetail packageDetail=new PackageDetail();
                    packageDetail.setClothesStyleColor(color);
                    packageDetail.setClothesStyleName(size);
                    packageDetail.setClothesAmount(Integer.valueOf(number));
                    packageDetailArrayList.add(packageDetail);
                    packageAdapter.notifyDataSetChanged();
                }
            }
        });
        packageAddPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=ProgressDialog.show(getActivity(),"请稍等","",true,true);
                addPackage();
            }
        });
        packageItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定删除?");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageDetail packageDetail= (PackageDetail) packageItemListView.getItemAtPosition(position);
                        packageDetailArrayList.remove(packageDetail);
                        packageAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
        packageDetailListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定删除相同包名的数据?");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PackageDetail packageDetail= (PackageDetail) packageDetailListView.getItemAtPosition(position);
                        removePackage(packageDetail.getPackageId());


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
        packageSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定提交?");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog=ProgressDialog.show(getActivity(),"请等待","数据上传中",true,true);
                        packageSubmit();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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

    public void addPackage(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",order.getOrderId());
        String size="";
        String color="";
        String number="";
        for(PackageDetail p:packageDetailArrayList){
             size=p.getClothesStyleName()+",";
             color=p.getClothesStyleColor()+",";
             number= String.valueOf(p.getClothesAmount())+",";
        }
        params.put("size",size);
        params.put("color",color);
        params.put("number",number);
        client.post(IPAddress.getIP()+packageAddPackageUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    int packageId= Integer.parseInt(response.getString("packageId"));
                    for(PackageDetail p:packageDetailArrayList){
                        p.setPackageId(packageId);
                    }
                    totalPackageDetailArrayList.addAll(packageDetailArrayList);
                    totalPackageAdapter.notifyDataSetChanged();
                    packageDetailArrayList.clear();
                    packageAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });

    }

    public void removePackage(final int packageNumber){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("packageId",String.valueOf(packageNumber));
        boolean result=false;
        client.post(IPAddress.getIP()+packageRemovePackageUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean result= Boolean.parseBoolean(response.getString("isSuccess"));
                    if(result){
                        ArrayList<PackageDetail> removePackageDetailArrayList=new ArrayList<PackageDetail>();
                        for(PackageDetail p :totalPackageDetailArrayList){
                            if(p.getPackageId() == packageNumber){
                                removePackageDetailArrayList.add(p);
                            }
                        }


                        totalPackageDetailArrayList.removeAll(removePackageDetailArrayList);
                        totalPackageAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),"删除出现错误",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void packageSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",order.getOrderId());
        client.post(IPAddress.getIP(),params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean result= Boolean.parseBoolean(response.getString("isSuccess"));
                    if(result){
                       Toast.makeText(getActivity(),"提交成功!请点击入库登记",Toast.LENGTH_SHORT).show();
                       getActivity().finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });
    }

    public void getPackageDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+packageDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success",response.toString());
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    ArrayList<Produce> produceArrayList=orderInfo.getProduceArrayList();
                    ArrayList<String> produceColorList=new ArrayList<String>();
                    for(Produce p:produceArrayList){
                        produceColorList.add(p.getColor());
                    }
                    ArrayAdapter spinnerAdapter= new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,produceColorList);
                    packageColorSpinner.setAdapter(spinnerAdapter);


                    totalPackageDetailArrayList=orderInfo.getPackageDetailArrayList();

                    totalPackageAdapter=new PackageAdapter(getActivity(),0,totalPackageDetailArrayList);
                    packageDetailListView.setAdapter(totalPackageAdapter);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();

            }
        });
    }


}
