package nju.zhizaolian.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.DeliverSampleFragment;
import nju.zhizaolian.fragments.ReceiveSampleFragment;

public class DepartmentLogisticActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_logistic);
        SpinnerAdapter logisticList= ArrayAdapter.createFromResource(this,R.array.logistic_list,R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(logisticList,onNavigationListener);
    }
    ActionBar.OnNavigationListener onNavigationListener=new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int i, long l) {
            Fragment newFragment=null;
            switch (i){
                case 0:newFragment=new ReceiveSampleFragment();break;
                case 1:newFragment=new DeliverSampleFragment();break;
                case 2: newFragment=new ReceiveSampleFragment();break;
                case 3:newFragment=new ReceiveSampleFragment();break;
                default:newFragment=new ReceiveSampleFragment();break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.logisticContainer,newFragment).commit();
            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_logistic, menu);
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
