package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment implements View.OnClickListener{
    private ListView listView;
    private Button registerButton;
    private Button editButton;
    private Button deleteButton;

    public CustomerListFragment() {
        // Required empty public constructor
    }

    public interface CustomerGoToEditFragmentButtonClickListener{
        void customerGoToEditFragmentButtonClicked();
    }

    public interface  CustomerDeleteButtonClickListener{
        boolean customerDeleteButtonClicked();
    }

    public interface CustomerGoToRegisterFragmentButtonClickListener{
        void customerGoToRegisterFragmentButtonClicked();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_list,container,false);
        listView = (ListView) view.findViewById(R.id.customer_info_list);
        ArrayList<HashMap<String,String>> customerList = new ArrayList<HashMap<String, String>>();
//        Test Data
        for(int i=0;i<10;i++){
            HashMap<String,String> item = new HashMap<String,String>();
            item.put("name",""+i);
            item.put("phone",""+i);
            item.put("address",""+i);
            item.put("company_name",""+i);
            item.put("company_phone",""+i);
            customerList.add(item);
        }
//       Test Data End
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),customerList,R.layout.customer_list_item,
                new String[]{"name","phone","address","company_name","company_phone"},
                new int[]{R.id.customer_name_string,R.id.customer_phone_string,R.id.customer_address_string,
                            R.id.customer_company_name_string,R.id.customer_company_phone_string});
        listView.setAdapter(simpleAdapter);
        registerButton=(Button) view.findViewById(R.id.customer_register_button);
        registerButton.setOnClickListener(this);
        editButton=(Button) view.findViewById(R.id.customer_edit_button);
        editButton.setOnClickListener(this);
        deleteButton=(Button) view.findViewById(R.id.customer_delete_button);
        deleteButton.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_register_button:
                ((CustomerGoToRegisterFragmentButtonClickListener) getActivity()).customerGoToRegisterFragmentButtonClicked();
                break;
            case R.id.customer_edit_button:
                ((CustomerGoToEditFragmentButtonClickListener) getActivity()).customerGoToEditFragmentButtonClicked();
                break;
            case R.id.customer_delete_button:
                ((CustomerDeleteButtonClickListener) getActivity()).customerDeleteButtonClicked();
                break;
        }
    }


}

