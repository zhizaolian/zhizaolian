package nju.zhizaolian.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import nju.zhizaolian.R;
import nju.zhizaolian.fragments.CustomerEditFragment;
import nju.zhizaolian.fragments.CustomerListFragment;
import nju.zhizaolian.fragments.CustomerRegisterFragment;

public class CustomerManagerActivity extends Activity implements
        CustomerRegisterFragment.CustomerRegisterButtonClickListener,
        CustomerRegisterFragment.CustomerCancelButtonClickListener,
        CustomerEditFragment.CustomerEditButtonClickListener,
        CustomerEditFragment.CustomerCancelButtonClickListener,
        CustomerListFragment.CustomerGoToRegisterFragmentButtonClickListener,
        CustomerListFragment.CustomerGoToEditFragmentButtonClickListener,
        CustomerListFragment.CustomerDeleteButtonClickListener{

    private CustomerEditFragment customerEditFragment;
    private CustomerListFragment customerListFragment;
    private CustomerRegisterFragment customerRegisterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_manager_layout);

        if(savedInstanceState==null) {
            customerListFragment = new CustomerListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.customer_manager_content, customerListFragment, "ONE");
            fragmentTransaction.commit();
        }
    }


    @Override
    public void customerCancelButtonClicked() {
        Toast.makeText(this,"canceled",Toast.LENGTH_SHORT).show();
        if(customerListFragment==null){
            customerListFragment=new CustomerListFragment();
        }
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customer_manager_content,customerListFragment,"ONE");
        fragmentTransaction.commit();
    }

    @Override
    public void customerRegisterButtonClicked() {
        Toast.makeText(this,"registered",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void customerEditButtonClicked() {

        Toast.makeText(this,"edited",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean customerDeleteButtonClicked() {
        Toast.makeText(this,"edited",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void customerGoToEditFragmentButtonClicked() {
        Toast.makeText(this,"go to edit",Toast.LENGTH_SHORT).show();
        if(customerEditFragment==null){
            customerEditFragment = new CustomerEditFragment();
        }
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customer_manager_content,customerEditFragment,"TWO");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void customerGoToRegisterFragmentButtonClicked() {
        Toast.makeText(this,"go to register",Toast.LENGTH_SHORT).show();
        if(customerRegisterFragment==null){
            customerRegisterFragment = new CustomerRegisterFragment();
        }
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customer_manager_content,customerRegisterFragment,"THREE");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
