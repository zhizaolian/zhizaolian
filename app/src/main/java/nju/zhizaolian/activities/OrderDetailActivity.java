package nju.zhizaolian.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderBaseInfoFragment;
import nju.zhizaolian.fragments.OrderMaterialDetailFragment;
import nju.zhizaolian.fragments.OrderProcessDetailFragment;
import nju.zhizaolian.fragments.OrderSampleDetailFragment;
import nju.zhizaolian.fragments.OrderVersionDetailFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Custom;

public class OrderDetailActivity extends ActionBarActivity implements ActionBar.TabListener ,
        OrderBaseInfoFragment.SaveBaseInfoData,OrderSampleDetailFragment.SaveSampleData {


    SectionsPagerAdapter mSectionsPagerAdapter;


    ViewPager mViewPager;
    private Custom custom=null;
    private Account account=null;
    //需要提交的数据
    //基本信息
    private boolean isHaoDuoYi;
    private String orderSource;
    private String styleName;
    private String clothesType;
    private String styleSex;
    private String styleSeason;
    private String materialType;
    private String specialProcess;
    private String otherRequirements;
    private String referenceUrl;
    //面辅信息
    private String fabricName;
    private String fabricAmount;
    private String accessory_name;
    private String accessoryAmount;
    //sample info
    private int hasPostedSampleClothes;
    private int isNeedSampleClothes;
    private String inPostSampleClothesTime;
    private String inPostSampleClothesType;
    private String inPostSampleClothesNumber;
    private String sampleClothesName;
    private String sampleClothesPhone;
    private String sampleClothesAddress;
    private String sampleClothesRemark;
    private String sampleClothesPicture;
    private String referenceClothesPicture;
    //process info
    private int  askAmount;
    private String askProducePeriod;
    private String askDeliverDate;
   // private int  ask_code_number;
    private String produceColor;
    private String produceXS;
    private String produceS;
    private String produceM;
    private String produceL;
    private String produceXL;
    private String produceXXL;
    private String produceJ;
    private String sampleProduceColor;
    private String sampleProduceXS;
    private String sampleProduceS;
    private String sampleProduceM;
    private String sampleProduceL;
    private String sampleProduceXL;
    private String sampleProduceXXL;
    private String sampleProduceJ;

    //version info
    private String versionSize;
    private String versionCenterBackLength;
    private String versionBust;
    private String versionWaistLine;
    private String versionShoulder;
    private String versionButtock;
    private String versionHem;
    private String versionTrousers;
    private String versionSkirt;
    private String versionSleeves;
    //
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
            return true;
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
    public void saveBaseInfoData(String orderSource) {
        Toast.makeText(getApplicationContext(),orderSource,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveSampleData(String sampleData) {
        Toast.makeText(getApplicationContext(),sampleData,Toast.LENGTH_SHORT).show();
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



}
