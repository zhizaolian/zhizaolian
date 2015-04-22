package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.WelcomeFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;

public class MainActivity extends ActionBarActivity implements
        WelcomeFragment.InquirySheetDownloadListener,
        WelcomeFragment.PriceSheetDownloadListener{

        private  WelcomeFragment welcomeFragment;
        private Context mContext;

        private long firstPressTime=0l; //双击退出参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        Account account= (Account) getIntent().getSerializableExtra("account");
        if(savedInstanceState==null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("account",account);
            welcomeFragment = new WelcomeFragment();
            welcomeFragment.setArguments(bundle);
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
        downloadSheets("inquirySheet");

    }

    @Override
    public void priceSheetDownload() {
        Toast.makeText(this,"报价单下载中",Toast.LENGTH_SHORT).show();
        downloadSheets("priceSheet");
    }

    public void downloadSheets(final String name){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url =IPAddress.getIP()+"/fmc/message/"+name+".doc";
        String[] allowContentType = {".*"};
        asyncHttpClient.get(url,new BinaryHttpResponseHandler(allowContentType) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                String tempPath = Environment.getExternalStorageDirectory().getPath()+"/"+name+".doc";

                setProgress(0);
                File file = new File(tempPath);
                Log.e("binaryData:", "共下载了：" + binaryData.length);
                try {
                    if(file.exists())
                        file.delete();
                    file.createNewFile();
                    FileOutputStream fileOutputStream =new FileOutputStream(file);
                    fileOutputStream.write(binaryData);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Toast.makeText(mContext,"下载成功\n"+tempPath,Toast.LENGTH_SHORT).show();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                Toast.makeText(mContext,"网络连接错误\n下载失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count=(int) ((bytesWritten*1.0/totalSize)*100);
                setProgress(count);
                Log.e("process",bytesWritten+"/"+totalSize);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(welcomeFragment.isDrawerOpen()) {
                welcomeFragment.closeDrawers();
            }else{
                if(System.currentTimeMillis()-firstPressTime>2000){
                    firstPressTime=System.currentTimeMillis();
                    Toast.makeText(this,"再点一次退出",Toast.LENGTH_SHORT).show();
                }else {
                    finish();
                }
            }
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
