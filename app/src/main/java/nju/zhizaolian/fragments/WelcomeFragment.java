package nju.zhizaolian.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener{

    private TextView inquirySheet;
    private TextView priceSheet;
    private TextView inquirySheetUploadTime;
    private TextView priceSheetUploadTime;
    private DrawerLayout drawerLayout;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public interface InquirySheetDownloadListener{
        void inquirySheetDownload();
    }

    public interface PriceSheetDownloadListener{
        void priceSheetDownload();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_layout,container,false);
        inquirySheet=(TextView) view.findViewById(R.id.inquiry_sheet_download);
        inquirySheet.setOnClickListener(this);
        priceSheet=(TextView) view.findViewById(R.id.price_sheet_download);
        priceSheet.setOnClickListener(this);
        inquirySheetUploadTime=(TextView) view.findViewById(R.id.inquiry_sheet_upload_time);
        priceSheetUploadTime=(TextView) view.findViewById(R.id.price_sheet_upload_time);
        //Test Data
        inquirySheetUploadTime.setText("2015-03-31");
        priceSheetUploadTime.setText("2015-04-01");
        //Test Data End
        drawerLayout =(DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(new MyDrawerListener());
        drawerLayout.setScrimColor(Color.TRANSPARENT);


        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inquiry_sheet_download:
                ((InquirySheetDownloadListener)getActivity()).inquirySheetDownload();
                break;
            case R.id.price_sheet_download:
                ((PriceSheetDownloadListener)getActivity()).priceSheetDownload();
                break;
        }
    }

    class MyDrawerListener implements DrawerLayout.DrawerListener{

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            View content = drawerLayout.getChildAt(0);
            View menu=drawerView;
            float scale = 1- slideOffset;
            float leftScale = 1-0.3f*scale;
            float rightScale = 0.8f + scale*0.2f;
            ViewHelper.setScaleX(menu, leftScale);
            ViewHelper.setScaleY(menu, leftScale);
            ViewHelper.setAlpha(menu, 0.6f + 0.4f * (1 - scale));
            ViewHelper.setTranslationX(content,
                    menu.getMeasuredWidth() * (1 - scale));
            ViewHelper.setPivotX(content, 0);
            ViewHelper.setPivotY(content,
                    content.getMeasuredHeight() / 2);
            content.invalidate();
            ViewHelper.setScaleX(content, rightScale);
            ViewHelper.setScaleY(content, rightScale);
            menu.setFocusableInTouchMode(true);

        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerClosed(View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }
}
