package nju.zhizaolian.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.CustomAdapter;
import nju.zhizaolian.models.Custom;

public class CustomListActivity extends ActionBarActivity {
    private ArrayList<Custom> customList;
    private ListView customListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        customListView= (ListView) findViewById(R.id.custom_listView);
        customList=new ArrayList<Custom>();
        CustomAdapter customAdapter=new CustomAdapter(this,customList);
        customListView.setAdapter(customAdapter);
        Custom a=new Custom();
        a.setCustomerName("luck");
        a.setCustomerPhone("143423425");
        a.setCompanyName("hadohfo");
        a.setCompanyPhone("1242354235");
        a.setCompanyAddress("fdhfkashg");
        customAdapter.add(a);
        Custom b=new Custom();
        b.setCustomerName("luck");
        b.setCustomerPhone("143423425");
        b.setCompanyName("hadohfo");
        b.setCompanyPhone("1242354235");
        b.setCompanyAddress("fdhfkashg");

        customAdapter.add(b);
         Custom a1=a;
        customAdapter.add(a1);
        Custom a2=a;
        customAdapter.add(a2);
        Custom a3=a;
        customAdapter.add(a3);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                opensearch();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void opensearch(){
        //TODO 打开搜索
        Toast.makeText(this,"opensearch",Toast.LENGTH_SHORT).show();
    }
}
