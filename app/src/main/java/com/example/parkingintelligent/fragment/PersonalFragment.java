package com.example.parkingintelligent.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.page.ParkUploadPage;

public class PersonalFragment extends Fragment {
    protected View _view;
    protected ViewGroup _container;
    protected Button _parkFieldAddButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.personal_page, container, false);
        _container = container;
        _parkFieldAddButton = _view.findViewById(R.id.AddParkFiledButton);

        // 添加点击事件

        _parkFieldAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到上传停车位页面
                Intent intent =new Intent(_view.getContext(), ParkUploadPage.class);
                startActivity(intent);
            }
        });


        return _view;
    }
}
