package nju.zhizaolian.activities;


import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentDesignActivity extends ActionBarActivity{

    OrderListFragment orderListFragment;
    public static final int SAMPLE_PRODUCTION=0;
    public static final int SLICE=1;
    public static final int VERSION_CONFIRM=2;
    int selectedSpinnerItem = SAMPLE_PRODUCTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_design_activity_layout);
        Account account =(Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber =(TaskNumber) getIntent().getSerializableExtra("taskNumber");
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.design_department_list, R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);
        if(savedInstanceState==null){
            orderListFragment = new OrderListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_design_activity_layout,orderListFragment,"orderList");
            fragmentTransaction.commit();
        }
    }

    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case SAMPLE_PRODUCTION:
                    getSampleProduceList();
                    selectedSpinnerItem=SAMPLE_PRODUCTION;
                    break;
                case SLICE:
                    getSliceList();
                    selectedSpinnerItem=SLICE;
                    break;
                case VERSION_CONFIRM:
                    getVersionConfirmList();
                    selectedSpinnerItem=VERSION_CONFIRM;
                    break;
                default:break;
            }
            return false;
        }
    };

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

        }

        return super.onOptionsItemSelected(item);
    }

    public void getVersionConfirmList(){
        String url = "/fmc/design/mobile_getConfirmCadList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.DESIGN_CONFIRM);
    }

    public void getSliceList(){
        String url ="/fmc/design/mobile_getTypeSettingSliceList.do";
        orderListFragment.getListViewByURLAndOperation(url,Operation.DESIGN_SLICE);
    }

    public void getSampleProduceList(){
        String url ="/fmc/design/mobile_getUploadDesignList.do";
        orderListFragment.getListViewByURLAndOperation(url,Operation.DESIGN_ENTERING);
    }


   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(getFragmentManager().getBackStackEntryCount()>0) {
                getFragmentManager().popBackStack();
                getSupportActionBar().show();
                switch (selectedSpinnerItem){
                    case SAMPLE_PRODUCTION:
                        getSampleProduceList();
                        break;
                    case SLICE:
                        getSliceList();
                        break;
                    case VERSION_CONFIRM:
                        getVersionConfirmList();
                        break;
                    default:
                        break;

                }
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
