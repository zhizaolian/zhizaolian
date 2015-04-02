package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.WelcomeFragment;

public class MainActivity extends ActionBarActivity implements
        WelcomeFragment.InquirySheetDownloadListener,
        WelcomeFragment.PriceSheetDownloadListener{

    private  WelcomeFragment welcomeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null) {
            welcomeFragment = new WelcomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.welcome_activity, welcomeFragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean  onOptionsItemSelected(MenuItem item) {
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

    @Override
    public void inquirySheetDownload() {
        Toast.makeText(this,"询价单下载中",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void priceSheetDownload() {
        Toast.makeText(this,"价格单下载中",Toast.LENGTH_SHORT).show();
    }
}
