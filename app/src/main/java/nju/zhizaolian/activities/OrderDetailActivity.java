package nju.zhizaolian.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderBaseInfoFragment;
import nju.zhizaolian.fragments.OrderMaterialDetailFragment;
import nju.zhizaolian.fragments.OrderProcessDetailFragment;
import nju.zhizaolian.fragments.OrderSampleDetailFragment;
import nju.zhizaolian.fragments.OrderVersionDetailFragment;
import nju.zhizaolian.help.FormFile;
import nju.zhizaolian.help.UpLoadUtil;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Custom;
import nju.zhizaolian.models.IPAddress;

public class OrderDetailActivity extends ActionBarActivity implements ActionBar.TabListener ,
        OrderBaseInfoFragment.SaveBaseInfoData,OrderSampleDetailFragment.SaveSampleData,OrderProcessDetailFragment.SaveProcessData,
        OrderMaterialDetailFragment.SaveFabricData,OrderVersionDetailFragment.SaveVerisonData{


    SectionsPagerAdapter mSectionsPagerAdapter;


    ViewPager mViewPager;
    private Custom custom=null;
    private Account account=null;
    //需要提交的数据
    //基本信息
    private int customID;
    private short isHaoDuoYi;
    private String orderSource="";
    private String styleName="";
    private String clothesType="";
    private String styleSex="";
    private String styleSeason="";
    private String materialType="";
    private String specialProcess="";
    private String otherRequirements="";
    private String referenceUrl="";
    //面辅信息
    private String fabricName="";
    private String fabricAmount="";
    private String accessoryName="";
    private String accessoryAmount="";
    //sample info
    private short hasPostedSampleClothes;//0,1,2
    private short isNeedSampleClothes;//0,1
    private String inPostSampleClothesTime="";
    private String inPostSampleClothesType="";
    private String inPostSampleClothesNumber="";
    private String sampleClothesName="";
    private String sampleClothesPhone="";
    private String sampleClothesAddress="";
    private String sampleClothesRemark="";
    private String sampleClothesPicture="";
    private String referenceClothesPicture="";
    //process info
    private String  askAmount="";
    private String askProducePeriod="";
    private String askDeliverDate="";
   // private int  ask_code_number;
    private String produceColor="";
    private String produceXS="";
    private String produceS="";
    private String produceM="";
    private String produceL="";
    private String produceXL="";
    private String produceXXL="";
    private String produceJ="";
    private String sampleProduceColor="";
    private String sampleProduceXS="";
    private String sampleProduceS="";
    private String sampleProduceM="";
    private String sampleProduceL="";
    private String sampleProduceXL="";
    private String sampleProduceXXL="";
    private String sampleProduceJ="";

    //version info
    private String versionSize="";
    private String versionCenterBackLength="";
    private String versionBust="";
    private String versionWaistLine="";
    private String versionShoulder="";
    private String versionButtock="";
    private String versionHem="";
    private String versionTrousers="";
    private String versionSkirt="";
    private String versionSleeves="";

    //picture byte
    private byte[] samplePictureByte;
    private byte[] referencePictureByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        custom= (Custom) getIntent().getSerializableExtra("custom");
        account= (Account) getIntent().getSerializableExtra("account");
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submitData) {
            if(orderSource==""){
                Toast.makeText(getApplicationContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
            }else if(styleName==""){
                Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
            }else if (referenceUrl=="") {
                Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_SHORT).show();
            } else if ( inPostSampleClothesTime=="") {
                Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_SHORT).show();
            }else if (inPostSampleClothesType==""){
                Toast.makeText(getApplicationContext(),"5",Toast.LENGTH_SHORT).show();
            }else if (inPostSampleClothesNumber==""){
                Toast.makeText(getApplicationContext(),"6",Toast.LENGTH_SHORT).show();
            }else if (sampleClothesName==""){
                Toast.makeText(getApplicationContext(),"7",Toast.LENGTH_SHORT).show();
            }else if (sampleClothesPhone==""){
                Toast.makeText(getApplicationContext(),"8",Toast.LENGTH_SHORT).show();
            }else if (sampleClothesAddress==""){
                Toast.makeText(getApplicationContext(),"9",Toast.LENGTH_SHORT).show();
            }else if (sampleClothesRemark==""){
                Toast.makeText(getApplicationContext(),"10",Toast.LENGTH_SHORT).show();
            }else if (sampleClothesPicture==""){
                Toast.makeText(getApplicationContext(),"11",Toast.LENGTH_SHORT).show();
            }else if (referenceClothesPicture==""){
                Toast.makeText(getApplicationContext(),"12",Toast.LENGTH_SHORT).show();
            }else if (askProducePeriod==""){
                Toast.makeText(getApplicationContext(),"13",Toast.LENGTH_SHORT).show();
            }else if (askDeliverDate==""){
                Toast.makeText(getApplicationContext(),"14",Toast.LENGTH_SHORT).show();
            }else if (produceColor==""){
                Toast.makeText(getApplicationContext(),"15",Toast.LENGTH_SHORT).show();
            }else if (sampleProduceColor==""){
                Toast.makeText(getApplicationContext(),"16",Toast.LENGTH_SHORT).show();
            }else if (askAmount==""){


            }else{
                SubmitOrderTask task=new SubmitOrderTask();
                task.execute();
            }
        }else if(id == R.id.action_deleteOrderData){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void saveBaseInfoData(String baseInfo) {
        //Toast.makeText(getApplicationContext(),baseInfo,Toast.LENGTH_SHORT).show();

        String[] s=baseInfo.split(",");

       boolean b= Boolean.parseBoolean(s[0]);
        if(b==false){
            isHaoDuoYi=0;
        }else{
            isHaoDuoYi=1;
        }
        orderSource=s[1];
        styleName=s[2];
        clothesType=s[3];
        styleSex=s[4];
        styleSeason=s[5];
        materialType=s[6];
        specialProcess=s[7];
        otherRequirements=s[8];
        referenceUrl=s[9];
        customID= Integer.parseInt(s[10]);
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveSampleData(String sampleData) {
        //Toast.makeText(getApplicationContext(),sampleData,Toast.LENGTH_SHORT).show();
        String[] temp=sampleData.split("@");
        boolean ab= Boolean.parseBoolean(temp[0]);
        if(!ab){
            hasPostedSampleClothes=0;
        }else{
            hasPostedSampleClothes=1;
        }

        inPostSampleClothesTime=temp[1];
        inPostSampleClothesType=temp[2];
        inPostSampleClothesNumber=temp[3];
        boolean bb=Boolean.parseBoolean(temp[4]);
        if(!bb){
            isNeedSampleClothes=0;
        }else {
            isNeedSampleClothes=1;
        }

        sampleClothesName=temp[5];
        sampleClothesPhone=temp[6];
        sampleClothesAddress=temp[7];
        sampleClothesRemark=temp[8];
        sampleClothesPicture=temp[9];
        referenceClothesPicture=temp[10];
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveSamplePicture(byte[] samplePictureByte) {
        this.samplePictureByte=samplePictureByte;
    }

    @Override
    public void saveReference(byte[] referencePictureByte) {
        this.referencePictureByte=referencePictureByte;
    }

    @Override
    public void saveProcessData(String processData) {
        //Toast.makeText(getApplicationContext(),processData,Toast.LENGTH_SHORT).show();
        String[] k=processData.split("@");
        askAmount= k[0];
        Log.d("askamout",askAmount);
        askDeliverDate=k[1];
        askProducePeriod=k[2];
        produceColor=k[3];
         produceXS=k[4];
         produceS=k[5];
         produceM=k[6];
         produceL=k[7];
        produceXL=k[8];
        produceXXL=k[9];
        produceJ=k[10];
         sampleProduceColor=k[11];
         sampleProduceXS=k[12];
         sampleProduceS=k[13];
         sampleProduceM=k[14];
        sampleProduceL=k[15];
         sampleProduceXL=k[16];
        sampleProduceXXL=k[17];
         sampleProduceJ=k[18];
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void saveFabricData(String fabricdata) {
        String[] f=fabricdata.split("@");
            fabricName=f[0];
            fabricAmount=f[1];
            accessoryName=f[2];
            accessoryAmount=f[3];

        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveVersionData(String versionData) {
        String[] version=versionData.split("@");
        versionSize=version[0];
        versionCenterBackLength=version[1];
        versionBust=version[2];
        versionWaistLine=version[3];
        versionShoulder=version[4];
        versionButtock=version[5];
        versionHem=version[6];
        versionTrousers=version[7];
        versionSkirt=version[8];
        versionSleeves=version[9];
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return new OrderBaseInfoFragment(custom,account);
                case 1:
                    return new OrderMaterialDetailFragment();
                case 2:
                    return new OrderSampleDetailFragment();
                case 3:
                    return new OrderProcessDetailFragment();
                case 4:
                    return new OrderVersionDetailFragment();
            }
           return null;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);
            }
            return null;
        }
    }
    public class SubmitOrderTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            Map<String,String> requestParams=new HashMap<String,String>();
            SharedPreferences sharedPreferences=getSharedPreferences("common",0);
            String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
            requestParams.put("jsessionId",jsessionId);
            requestParams.put("customerId",String.valueOf(customID));
            requestParams.put("style_name",styleName);
            requestParams.put("fabric_type",materialType);
            requestParams.put("clothes_type",clothesType);
            requestParams.put("style_sex",styleSex);
            requestParams.put("style_season",styleSeason);
            requestParams.put("special_process",specialProcess);
            requestParams.put("other_requirements",otherRequirements);
            requestParams.put("reference_url",referenceUrl);
            requestParams.put("ask_amount",askAmount);
            requestParams.put("ask_produce_period",askProducePeriod);
            requestParams.put("ask_deliver_date",askDeliverDate);

            requestParams.put("has_posted_sample_clothes",String.valueOf(hasPostedSampleClothes));
            requestParams.put("is_need_sample_clothes",String.valueOf(isNeedSampleClothes));
            requestParams.put("order_source",orderSource);
           // requestParams.put("sample_clothes_picture",sampleClothesPicture);
           // requestParams.put("reference_picture",referenceClothesPicture);
            requestParams.put("fabric_name",fabricName);
            requestParams.put("fabric_amount",fabricAmount);
            requestParams.put("accessory_name",accessoryName);
            requestParams.put("accessory_query",accessoryAmount);
            requestParams.put("produce_color",produceColor);
            requestParams.put("produce_xs",produceXS);
            requestParams.put("produce_s",produceS);
            requestParams.put("produce_m",produceM);
            requestParams.put("produce_l",produceL);
            requestParams.put("produce_xl",produceXL);
            requestParams.put("produce_xxl",produceXXL);
            requestParams.put("produce_j",produceJ);

            requestParams.put("sample_produce_color",sampleProduceColor);
            requestParams.put("sample_produce_xs",sampleProduceXS);
            requestParams.put("sample_produce_s",sampleProduceS);
            requestParams.put("sample_produce_m",sampleProduceM);
            requestParams.put("sample_produce_l",sampleProduceL);
            requestParams.put("sample_produce_xl",sampleProduceXL);
            requestParams.put("sample_produce_xxl",sampleProduceXXL);
            requestParams.put("sample_produce_j",sampleProduceJ);


            requestParams.put("version_size",versionSize);
            requestParams.put("version_centerBackLength",versionCenterBackLength);
            requestParams.put("version_bust",versionBust);
            requestParams.put("version_waistLine",versionWaistLine);
            requestParams.put("version_shoulder",versionShoulder);
            requestParams.put("version_buttock",versionButtock);
            requestParams.put("version_hem",versionHem);
            requestParams.put("version_trousers",versionTrousers);
            requestParams.put("version_skirt",versionSkirt);
            requestParams.put("version_sleeves",versionSleeves);




            requestParams.put("in_post_sample_clothes_time",inPostSampleClothesTime);
            requestParams.put("in_post_sample_clothes_type",inPostSampleClothesType);
            requestParams.put("in_post_sample_clothes_number",inPostSampleClothesNumber);
            requestParams.put("sample_clothes_name",sampleClothesName);
            requestParams.put("sample_clothes_phone",sampleClothesName);
            requestParams.put("sample_clothes_address",sampleClothesAddress);
            requestParams.put("sample_clothes_remark",sampleClothesRemark);

            requestParams.put("is_haoduoyi",String.valueOf(isHaoDuoYi));
            requestParams.put("marketStaffId", String.valueOf(account.getUserId()));
            FormFile file1=new FormFile(sampleClothesPicture,samplePictureByte,"sample_clothes_picture",null);
            FormFile file2=new FormFile(referenceClothesPicture,referencePictureByte,"reference_picture",null);
            FormFile[] totalFile=new FormFile[2];
            totalFile[0]=file1;
            totalFile[1]=file2;
            boolean success= false;
            try {
                success = UpLoadUtil.post(IPAddress.getIP() + "/fmc/market/mobile_addOrderSubmit.do", requestParams, totalFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return success;
        }
    }



    }


