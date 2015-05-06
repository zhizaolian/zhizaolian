package nju.zhizaolian.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.OrderFragmentPagerAdapter;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.OrderInfo;

public class OrderShowActivity extends ActionBarActivity {
    private static String orderDetailUrl="/fmc/order/mobile_orderDetail.do";
    private ListInfo listInfo;
    private OrderInfo orderInfo;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_show);


        Bundle bundle=getIntent().getBundleExtra("bundle");
        listInfo= (ListInfo) bundle.getSerializable("info");
        progressDialog=ProgressDialog.show(this,"请等待","数据下载中",true,true);
        getOrderDetail();

    }


    public void getOrderDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+orderDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    if(orderInfo != null){
                        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
                        OrderFragmentPagerAdapter orderFragmentPagerAdapter=new OrderFragmentPagerAdapter(getSupportFragmentManager(),orderInfo);
                        viewPager.setAdapter(orderFragmentPagerAdapter);
                        PagerSlidingTabStrip tabStrip=(PagerSlidingTabStrip)findViewById(R.id.tabs);
                        tabStrip.setViewPager(viewPager);

                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }




}
