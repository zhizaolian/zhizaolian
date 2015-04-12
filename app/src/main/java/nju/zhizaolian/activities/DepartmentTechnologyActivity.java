package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.DepartmentTechnologyDesignFragment;
import nju.zhizaolian.fragments.DepartmentTechnologyMassFragment;
import nju.zhizaolian.fragments.DepartmentTechnologySampleFragment;

public class DepartmentTechnologyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_technology_activity_layout);
        if(savedInstanceState==null){
//            DepartmentTechnologyMassFragment departmentTechnologyMassFragment = new DepartmentTechnologyMassFragment();
//            DepartmentTechnologySampleFragment departmentTechnologySampleFragment = new DepartmentTechnologySampleFragment();
            DepartmentTechnologyDesignFragment departmentTechnologyDesignFragment = new DepartmentTechnologyDesignFragment();
            Bundle bundle = new Bundle();
            ArrayList<String> data = new ArrayList<>();
            for(int i=0;i<7;i++){
                data.add("0000");
            }
            bundle.putSerializable("data",data);
            bundle.putSerializable("time","20150302 20:13:21");
//            departmentTechnologyMassFragment.setArguments(bundle);
            departmentTechnologyDesignFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_technology_activity_layout,departmentTechnologyDesignFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_technology, menu);
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
