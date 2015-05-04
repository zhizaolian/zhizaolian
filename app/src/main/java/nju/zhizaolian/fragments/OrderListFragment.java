package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.OrderProcessListAdapter;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Operation;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment{

    private ListView orderListView;
    OrderProcessListAdapter adapter;
    ArrayList<HashMap<String,String>> dataList;
    ArrayList<ListInfo> orderInfoList;
    Operation operation;
    String list_url;
    SwipeRefreshLayout swipeRefreshLayout;
    Account account;
    ProgressDialog progressDialog;
    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_process_list,container,false);
        account =(Account)getArguments().getSerializable("account");
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.order_list_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListViewByURLAndOperation(list_url,operation);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(R.color.white,R.color.green,R.color.orange,R.color.red);
        orderListView =(ListView) view.findViewById(R.id.order_process_list);
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
        orderListView.setAdapter(adapter);
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToNextFragmentByIndex(position);
            }
        });
        return view;
    }

    private void updateListView(ArrayList<ListInfo> orderListInfo){
        if (orderListInfo==null){
            return;
        }
        orderInfoList=orderListInfo;
        dataList.clear();
        for(int i=0;i<orderListInfo.size();i++) {
            HashMap<String, String> data = new HashMap<>();
            data.put("name", orderListInfo.get(i).getOrder().getStyleName());
            data.put("number",""+orderListInfo.get(i).getOrderId());
            data.put("date", orderListInfo.get(i).getOrder().getOrderTime());
            data.put("state", orderListInfo.get(i).getOrder().getOrderProcessStateName());
            if(operation==Operation.RECEIVESAMPLE||operation==Operation.DELIVERSAMPLE){
                data.put("s_name", "无关紧要");
            }else {
                data.put("s_name", orderListInfo.get(i).getEmployee().getEmployeeName());
            }
            data.put("c_name", orderListInfo.get(i).getOrder().getCustomerName());
            data.put("cc_name", orderListInfo.get(i).getOrder().getCustomerCompany());
            data.put("image_url",orderListInfo.get(i).getOrder().getSampleClothesThumbnailPicture());
            data.put("big_image_url",orderListInfo.get(i).getOrder().getSampleClothesPicture());
            dataList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    public void getListViewByURLAndOperation(final String url, final Operation operate){
        progressDialog= ProgressDialog.show(getActivity(),"请等待","正在刷新列表",true);
        if(!isVisible()){
            getActivity().getFragmentManager().popBackStack();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        client.get(IPAddress.getIP()+url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    ArrayList<ListInfo> listInfoArrayList=new ArrayList<ListInfo>();
                    if(operate==Operation.WAREHOUSE){
                        listInfoArrayList =ListInfo.fromJson(response.getJSONArray("packageList"));

                        listInfoArrayList.addAll(ListInfo.fromJson(response.getJSONArray("packageHaoDuoYiList")));

                    }
                    else
                    {
                       listInfoArrayList =ListInfo.fromJson(response.getJSONArray("list"));
                    }

                    operation=operate;
                    list_url=url;
                    updateListView(listInfoArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                super.onFinish();
            }
        });


    }


    public void goToNextFragmentByIndex(int index){
        ListInfo listInfo = orderInfoList.get(index);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",listInfo);
        bundle.putSerializable("account",account);
        Fragment fragment = null;
        int id=0;
        switch (operation){
            case WAREHOUSE:
                fragment=new PackageFragment();
                id=R.id.logisticContainer;
                break;
            case MERGEPRICE:
                fragment = new MergePriceFragment();
                id=R.id.salesDepartmentcontainers;
                break;
            case QUOTEAGREED:
                fragment = new QuoteAgreedFragment();
                id=R.id.salesDepartmentcontainers;
                break;
            case CHANGEQUOTE:
                fragment = new ChangeQuoteFragment();
                id=R.id.salesDepartmentcontainers;
                break;
            case SIGNCONTRACT:
                fragment = new SignContractFragment();
                id=R.id.salesDepartmentcontainers;
                break;
            case URGEREMAININGBALANCE:
                fragment = new UrgeRemainingBalance();
                id=R.id.salesDepartmentcontainers;
                break;
            case CHECKQUOTE:
                fragment = new CheckQuoteFragment();
                id=R.id.salesMasterContainer;
                break;
            case CHANGEEMPLOYEE:
                fragment = new SalesManagerChangeEmployeeFragment();
                id=R.id.salesMasterContainer;
                break;
            case CHECKSAMPLEBALANCE:
                fragment = new CheckSampleBalanceFragment();
                id=R.id.financialContainer;
                break;
            case CHECKFRONTMONEY:
                fragment = new CheckFrontMoneyFragment();
                id=R.id.financialContainer;
                break;
            case RETURNMONEY:
                fragment = new ReturnMoneyFragment();
                id=R.id.financialContainer;
                break;
            case CHECKREMAININGBALANCE:
                fragment = new CheckRemainingBalanceFragment();
                id=R.id.financialContainer;
                break;
            case RECEIVESAMPLE:
                fragment = new ReceiveSampleFragment();
                id=R.id.logisticContainer;
                break;
            case DELIVERSAMPLE:
                fragment = new DeliverSampleFragment();
                id=R.id.logisticContainer;
                break;
            case CHECKQALITY:
                fragment = new CheckQualityFragment();
                id=R.id.qualityContainer;
                break;
            case DESIGN_SLICE:
                fragment = new DepartmentDesignSliceFragment();
                id=R.id.department_design_activity_layout;
                break;
            case DESIGN_ENTERING:
                fragment = new DepartmentDesignEnteringFragment();
                id=R.id.department_design_activity_layout;
                break;
            case DESIGN_CONFIRM:
                fragment = new DepartmentDesignConfirmFragment();
                id=R.id.department_design_activity_layout;
                break;
            case TECHNOLOGY_DESIGN:
                fragment = new DepartmentTechnologyDesignFragment();
                id=R.id.department_technology_activity_layout;
                break;
            case TECHNOLOGY_MASS:
                fragment = new DepartmentTechnologyMassFragment();
                id=R.id.department_technology_activity_layout;
                break;
            case TECHNOLOGY_SAMPLE:
                fragment = new DepartmentTechnologySampleFragment();
                id=R.id.department_technology_activity_layout;
                break;
            case PURCHASE_CHECK:
                fragment = new DepartmentPurchaseCheckFragment();
                id=R.id.department_purchase_activity_layout;
                break;
            case PURCHASE_MASS:
                fragment = new DepartmentPurchaseMassSampleFragment();
                id=R.id.department_purchase_activity_layout;
                bundle.putSerializable("TYPE","MASS");
                break;
            case PURCHASE_SAMPLE:
                fragment = new DepartmentPurchaseMassSampleFragment();
                id=R.id.department_purchase_activity_layout;
                bundle.putSerializable("TYPE","SAMPLE");
                break;
            case PURCHASE_SWEATER:
                fragment = new DepartmentPurchaseSweaterFragment();
                id=R.id.department_purchase_activity_layout;
                break;
            case PRODUCTION_CHECK:
                fragment = new DepartmentProductionCheckFragment();
                id=R.id.department_production_activity_layout;
                break;
            case PRODUCTION_MASS:
                fragment = new DepartmentProductionMassFragment();
                id=R.id.department_production_activity_layout;
                break;
            case SWEATER_MAKER_CHECK:
                fragment = new DepartmentSweaterMakerCheckFragment();
                id=R.id.department_sweater_maker_activity_layout;
                break;
            case SWEATER_MAKER_SENT:
                fragment = new DepartmentSweaterMakerSentFragment();
                id=R.id.department_sweater_maker_activity_layout;
                break;
            case ORDER_MANAGER_DETAIL:
                //TODO 跳到OrderDetailActivity？求Set方法！
                fragment = new Fragment();
                id=R.id.activity_order_list_layout;
                return;
            case SECRETARY_CHANGE:
                fragment = new DepartmentSecretaryChangeFragment();
                id=R.id.department_secretary_activity_layout;
                break;

        }
        fragment.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction().replace(id,fragment).addToBackStack(null).commit();
    }

}
