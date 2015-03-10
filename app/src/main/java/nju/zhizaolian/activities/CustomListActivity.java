package nju.zhizaolian.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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
        a.setName("luck");
        a.setPhone("143423425");
        customAdapter.add(a);
        Custom b=new Custom();
        b.setName("jack");
        b.setPhone("121421432");
        customAdapter.add(b);


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
