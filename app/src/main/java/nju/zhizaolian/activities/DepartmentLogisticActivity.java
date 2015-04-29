package nju.zhizaolian.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;

public class DepartmentLogisticActivity extends ActionBarActivity {
    private OrderListFragment orderListFragment;
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_logistic);
        SpinnerAdapter logisticList= ArrayAdapter.createFromResource(this,R.array.logistic_list,R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(logisticList,onNavigationListener);
        account= (Account) getIntent().getSerializableExtra("account");
        if(savedInstanceState == null){

            Bundle bundle=new Bundle();
            bundle.putSerializable("account",account);
            orderListFragment=new OrderListFragment();
            orderListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.logisticContainer,orderListFragment).commit();
        }
    }
    ActionBar.OnNavigationListener onNavigationListener=new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int i, long l) {

            switch (i){
                case 0:
                    orderListFragment.getListViewByURLAndOperation("/fmc/logistics/mobile_receiveSampleList.do", Operation.RECEIVESAMPLE);
                    break;
                case 1:orderListFragment.getListViewByURLAndOperation("/fmc/logistics/mobile_sendSampleList.do",Operation.DELIVERSAMPLE);break;
                case 2:Toast.makeText(getApplicationContext(), "暂不可用", Toast.LENGTH_SHORT).show();break;
                case 3:
                    Toast.makeText(getApplicationContext(), "暂不可用", Toast.LENGTH_SHORT).show();break;

                default:break;
            }

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
