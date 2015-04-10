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
import nju.zhizaolian.fragments.DepartmentProductionCheckFragment;
import nju.zhizaolian.fragments.DepartmentProductionMassFragment;

public class DepartmentProductionActivity extends ActionBarActivity {

    DepartmentProductionMassFragment departmentProductionMassFragment;
    DepartmentProductionCheckFragment departmentProductionCheckFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_production_activity_layout);
        if(savedInstanceState==null){
            //TODO test data
            ArrayList<HashMap<String,String>> list = new ArrayList<>();
            for(int i=0;i<4;i++){
                HashMap<String,String> data=new HashMap<>();
                data.put("颜色","白色");
                data.put("XS","1");
                data.put("S","2");
                data.put("M","3");
                data.put("L","4");
                data.put("XL","5");
                data.put("XXL","6");
                data.put("均码","7");
                list.add(data);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("plan_table",list);
            //data end
            departmentProductionMassFragment = new DepartmentProductionMassFragment();
            departmentProductionMassFragment.setArguments(bundle);
//            departmentProductionCheckFragment= new DepartmentProductionCheckFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.department_production_activity_layout, departmentProductionCheckFragment);
            fragmentTransaction.add(R.id.department_production_activity_layout, departmentProductionMassFragment);
            fragmentTransaction.commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_production, menu);
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
