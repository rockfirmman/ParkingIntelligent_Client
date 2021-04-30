package com.example.parkingintelligent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.parkingintelligent.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ParkExtraInfo extends Fragment {
    private View _view;
    private ViewGroup _container;
    private EditText _name;
    private EditText _description;
    private Button _save;
    protected String ParkDes;
    protected String ParkName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.park_extra_info, container, false);
        _container = container;
        _name = _view.findViewById(R.id.ParkFieldName);
        _description = _view.findViewById(R.id.ParkFieldBriefIntro);
        _save = _view.findViewById(R.id.SaveButton);
        _save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        return _view;
    }


    // 保存

    public void Save()
    {
        //TODO
        // 将Name和Description保存下来
        ParkName = _name.getText().toString();
        ParkDes = _description.getText().toString();
        // 提示
        new SweetAlertDialog(_view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("保存成功")
                .setConfirmText("确认！")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public String getParkDes() {
        return ParkDes;
    }

    public String getParkName() {
        return ParkName;
    }
}
