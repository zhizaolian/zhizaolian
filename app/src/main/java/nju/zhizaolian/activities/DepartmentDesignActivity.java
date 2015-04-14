package nju.zhizaolian.activities;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.DepartmentDesignConfirmFragment;
import nju.zhizaolian.fragments.DepartmentDesignEnteringFragment;
import nju.zhizaolian.fragments.DepartmentDesignSliceFragment;

public class DepartmentDesignActivity extends ActionBarActivity {

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_design_activity_layout);
        if(savedInstanceState==null){
//            DepartmentDesignConfirmFragment fragment = new DepartmentDesignConfirmFragment();
            DepartmentDesignEnteringFragment fragment = new DepartmentDesignEnteringFragment();
//            DepartmentDesignSliceFragment fragment = new DepartmentDesignSliceFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_design_activity_layout,fragment);
            fragmentTransaction.addToBackStack("null");
            fragmentTransaction.commit();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_design, menu);
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
            Intent intent = new Intent(DepartmentDesignActivity.this,DepartmentProductionActivity.class);
//            intent.putExtras(bundle);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
