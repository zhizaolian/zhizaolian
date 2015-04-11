package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.DepartmentPurchaseCheckFragment;
import nju.zhizaolian.fragments.DepartmentPurchaseMassSampleFragment;
import nju.zhizaolian.fragments.DepartmentPurchaseSweaterFragment;

public class DepartmentPurchaseActivity extends ActionBarActivity {

    DepartmentPurchaseCheckFragment departmentPurchaseCheckFragment;
    DepartmentPurchaseMassSampleFragment departmentPurchaseMassSampleFragment;
    DepartmentPurchaseSweaterFragment departmentPurchaseSweaterFragment;
    String fabricTableHead[] = new String[]{"面料名称","单件米耗","件数","总采购米数"};
    String accessoryTableHead[] = new String[]{"辅料名称","单件耗数","件数","总采购个数"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_purchase_activity_layout);
        //TODO 采购部核算fragment
//        if(savedInstanceState==null){
//            departmentPurchaseCheckFragment=new DepartmentPurchaseCheckFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.department_purchase_activity_layout,departmentPurchaseCheckFragment);
//            fragmentTransaction.commit();
//        }
        //TODO 采购部大货和样衣fragment
//            if(savedInstanceState==null){
//                ArrayList<HashMap<String,String>> list = new ArrayList<>();
//                for(int i=0;i<6;i++){
//                    HashMap<String,String> map = new HashMap<>();
//                    for(int j=0;j<4;j++){
//                        map.put(fabricTableHead[j],i+"");
//                    }
//                    list.add(map);
//                }
//                ArrayList<HashMap<String,String>> list2 = new ArrayList<>();
//                for(int i=0;i<6;i++){
//                    HashMap<String,String> map = new HashMap<>();
//                    for(int j=0;j<4;j++){
//                        map.put(accessoryTableHead[j],i+"");
//                    }
//                    list2.add(map);
//                }
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("fabric_purchase",list);
//                bundle.putSerializable("accessory_purchase",list2);
//                departmentPurchaseMassSampleFragment = new DepartmentPurchaseMassSampleFragment();
//                departmentPurchaseMassSampleFragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.department_purchase_activity_layout,departmentPurchaseMassSampleFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
            //TODO 采购部毛衣采购fragment
        if(savedInstanceState==null){
            departmentPurchaseSweaterFragment = new DepartmentPurchaseSweaterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_purchase_activity_layout,departmentPurchaseSweaterFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_purchase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
