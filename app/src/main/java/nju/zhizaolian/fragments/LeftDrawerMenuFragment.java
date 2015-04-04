package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftDrawerMenuFragment extends Fragment {

    private ListView listView;

    public LeftDrawerMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_drawer_menu,container,false);
        listView=(ListView) view.findViewById(R.id.menuList);
        ArrayList<HashMap<String,String>> data=new ArrayList<>();

        // Test Data
        String department="salesmen";
        switch (department){
            case "salesmen":
                HashMap<String,String> item = new HashMap<>();
                item.put("department","市场部");
                item.put("number","5");
                data.add(item);
                break;
            case "sales":
                break;
            case "product":
                break;
            case "design":
                break;
            case  "admin":
                break;
            default:
                break;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this.getActivity(),
                data,
                R.layout.left_drawer_menu_item,
                new String[]{"department","number"},
                new int[]{R.id.item_department,R.id.item_department_number});
        listView.setAdapter(simpleAdapter);

        return view;

    }

}
