package com.example.parkingintelligent.page;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Person;
import android.os.Bundle;
import android.util.Log;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.fragment.BDMapView;
import com.example.parkingintelligent.fragment.BillFragment;
import com.example.parkingintelligent.fragment.PersonalFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BDMapView _bdMapView;
    private BottomBar _bottomBar;
    private BillFragment _billFragment;
    private PersonalFragment _personalFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 碎片管理
        fragmentManager =getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setContentView(R.layout.activity_main);
        requestPermissions();

        // 获得控件
        _bdMapView = new BDMapView();
        _billFragment = new BillFragment();
        _personalFragment = new PersonalFragment();

        fragmentTransaction.add(R.id.mainContent, _bdMapView);
        fragmentTransaction.add(R.id.mainContent, _billFragment);
        fragmentTransaction.hide( _billFragment);
        fragmentTransaction.add(R.id.mainContent, _personalFragment);
        fragmentTransaction.hide(_personalFragment);

        fragmentTransaction.commit();
        _bottomBar = findViewById(R.id.bottomBar);

        _bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                // 切换
                fragmentTransaction= fragmentManager.beginTransaction();
                int temp =_bottomBar.getCurrentTabId();
                switch (temp)
                {
                    case R.id.tab_home:
                        fragmentTransaction.hide(_bdMapView);
                        break;

                    case R.id.tab_bill:
                        fragmentTransaction.hide(_billFragment);
                        break;

                    case R.id.tab_person:
                        fragmentTransaction.hide(_personalFragment);
                        break;
                }
                switch (tabId)
                {
                    case R.id.tab_home:
                        fragmentTransaction.show(_bdMapView);
                        break;

                    case R.id.tab_bill:
                        fragmentTransaction.show(_billFragment);
                        break;

                    case R.id.tab_person:
                        fragmentTransaction.show(_personalFragment);

                        break;
                }
                fragmentTransaction.commit();
            }
        });




    }
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("TAG", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("TAG", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("TAG", permission.name + " is denied.");
                        }
                    }
                });


    }
}