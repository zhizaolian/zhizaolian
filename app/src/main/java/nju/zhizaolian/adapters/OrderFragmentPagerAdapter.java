package nju.zhizaolian.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nju.zhizaolian.fragments.OrderBaseInfoShowFragment;
import nju.zhizaolian.fragments.OrderProcessShowFragment;
import nju.zhizaolian.fragments.OrderSampleShowFragment;
import nju.zhizaolian.fragments.OrderVersionShowFragment;
import nju.zhizaolian.models.OrderInfo;

/**
 * Created by lk on 15/5/5.
 */
public class OrderFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT=4;
    private String[] pageTitles=new String[]{"基本信息","样衣信息","加工信息","版型信息"};
    private OrderInfo orderInfo;

    public OrderFragmentPagerAdapter(FragmentManager fm,OrderInfo orderInfo) {
        super(fm);
        this.orderInfo=orderInfo;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment newFragment=null;
        Bundle bundle=new Bundle();
        bundle.putSerializable("info",orderInfo);

        switch (position){
            case 0:newFragment= new OrderBaseInfoShowFragment();break;
            case 1:newFragment= new OrderSampleShowFragment();break;
            case 2:newFragment= OrderProcessShowFragment.newInstance(bundle);break;
            case 3:newFragment= OrderVersionShowFragment.newInstance(bundle);break;
        }
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
