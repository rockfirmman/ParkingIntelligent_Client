package com.example.parkingintelligent.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.parkingintelligent.R;

import static android.app.Activity.RESULT_CANCELED;

public class ImageUpload extends Fragment {
    protected Button _imgButton;
    protected ImageView _imgView;
    protected View _view;
    protected ViewGroup _container;

    private Uri _uri;
    private static final int CODE_PHOTO_REQUEST = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.image_upload, container, false);
        _container = container;
        _imgButton = _view.findViewById(R.id.imageButton);
        _imgView = _view.findViewById(R.id.ImageContent);
        _imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用相册
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,CODE_PHOTO_REQUEST);
            }
        });
        return _view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
         if (resultCode == RESULT_CANCELED) {
            Toast.makeText(_view.getContext(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode)
        {
            case CODE_PHOTO_REQUEST:
                // 本地相册
                if(data!= null)
                {
                    Uri uri = data.getData();
                    _imgView.setImageURI(uri);
                    _uri =uri;
                }
                break;
        }
         super.onActivityResult(requestCode, resultCode, data);
     }

    public Uri get_uri() {
        return _uri;
    }
}
