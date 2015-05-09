package nju.zhizaolian.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.WarehouseScanningAdapter;
import nju.zhizaolian.models.*;
import nju.zhizaolian.models.Package;

public class WarehouseScanningActivity extends ActionBarActivity {



    private Button scanningButton;
    private EditText packageIdEdit;
    private EditText wareHouseIdEdit;
    private EditText shelfIdEdit;
    private EditText locationEdit;
    private Button wareHouseUpdatePackageButton;
    private Button wareHouseSubmitButton;
    private ListView wareHouseListView;
    private WarehouseScanningAdapter warehouseScanningAdapter;
    private ArrayList<nju.zhizaolian.models.Package> packages=new ArrayList<Package>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_scanning);
        scanningButton=(Button)findViewById(R.id.start_scenning_button);
         packageIdEdit=(EditText)findViewById(R.id.package_number_edit);
        wareHouseIdEdit=(EditText)findViewById(R.id.warehouse_id_Edit);
         shelfIdEdit=(EditText)findViewById(R.id.shelf_id_edit);
         locationEdit=(EditText)findViewById(R.id.location_edit);
        wareHouseUpdatePackageButton=(Button)findViewById(R.id.warehouse_update_package_button);
         wareHouseSubmitButton=(Button)findViewById(R.id.warehouse_submit_button);
         wareHouseListView=(ListView)findViewById(R.id.warehouseScanningListview);


    }


}
