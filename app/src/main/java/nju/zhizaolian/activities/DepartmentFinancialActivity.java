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
import nju.zhizaolian.fragments.CheckRemainingBalanceFragment;

public class DepartmentFinancialActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_financial);
        SpinnerAdapter financiallist= ArrayAdapter.createFromResource(this,R.array.financial_list,R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(financiallist,navigationListener);
    }
    ActionBar.OnNavigationListener navigationListener=new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int i, long l) {
            Fragment fragment=null;
            switch (i){
                case 0:fragment= new CheckRemainingBalanceFragment();break;
                case 1:fragment= new CheckRemainingBalanceFragment();break;
                case 2: fragment= new CheckRemainingBalanceFragment();break;
                case 3:fragment= new CheckRemainingBalanceFragment();break;
                default:fragment= new CheckRemainingBalanceFragment();break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.financialContainer,fragment).commit();
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_financial, menu);
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
