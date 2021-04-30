package com.example.parkingintelligent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.ParkUploadData;

public class ParkFieldConfirm extends Fragment {
    protected View _view;
    protected ViewGroup _container;
    private TextView _currentName;
    private TextView _currentLag;
    private EditText _currentIntro;
    private ImageView _imageView;
    private String _nameContent;
    private String _desContent;
    private String _lagContent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.park_field_confirm, container, false);
        _container = container;

        // 得到控件
        _currentName = _view.findViewById(R.id.currentFieldName);
        _currentLag = _view.findViewById(R.id.currentLag);
        _currentIntro = _view.findViewById(R.id.currentIntro);
        _imageView = _view.findViewById(R.id.PlaneImage);

        return _view;
    }

    // 用数据设置所有
    public void SetByUploadData(ParkUploadData data)
    {
        this._currentName.setText(data.pname);
        this._currentIntro.setText(data.description);
        this._imageView.setImageURI(data.uri);
        this._currentLag.setText( "经度："+data.latitude+"纬度："+data.longitude);
        this._currentIntro.setEnabled(false);
    }
}
