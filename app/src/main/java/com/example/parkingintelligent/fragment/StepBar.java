package com.example.parkingintelligent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anton46.stepsview.StepsView;
import com.example.parkingintelligent.R;

public class StepBar extends Fragment {
    public int _position;
    private String[] _labels;
    private int _bg_color;
    private int _complete_color;
    private View _view;
    private StepsView _stepsView;
    private ViewGroup _container;
    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        _view = inflater.inflate(R.layout.step_bar, container, false);
        _container = container;
        if (getArguments() != null) {
            _position = getArguments().getInt("pos");
            _labels = getArguments().getStringArray("labels");
            _bg_color = getArguments().getInt("bg_color");
            _complete_color = getArguments().getInt("complete_color");
            _stepsView = (StepsView)  _view.findViewById(R.id.stepsView);
            _stepsView.setCompletedPosition(_position)
                    .setLabels(_labels)
                    .setBarColorIndicator(
                            container.getResources().getColor(_bg_color))
                    .setProgressColorIndicator(container.getContext().getResources().getColor(_complete_color))
                    .setLabelColorIndicator(container.getContext().getResources().getColor(_complete_color))
                    .drawView();
        }
        return _view;
    }

    public void DrawView()
    {
        _stepsView.setCompletedPosition(_position)
                .setLabels(_labels)
                .setBarColorIndicator(
                        _container.getResources().getColor(_bg_color))
                .setProgressColorIndicator(_container.getContext().getResources().getColor(_complete_color))
                .setLabelColorIndicator(_container.getContext().getResources().getColor(_complete_color))
                .drawView();
    }


//    传入初始化的步骤控件
    public static StepBar newInstance(int pos, String[] labels, int bg_color, int complete_color) {
        StepBar fragment = new StepBar();
        Bundle args = new Bundle();
        args.putInt("pos",pos);
        args.putStringArray("labels",labels);
        args.putInt("bg_color",bg_color);
        args.putInt("complete_color",complete_color);
        fragment.setArguments(args);
        return fragment;
    }
    public void Next()
    {
        if(_position<_labels.length-1)
            _position+=1;
        DrawView();
    }
    public void Previous()
    {
        if(_position>0)
            _position-=1;
        DrawView();
    }
    // 处于最后一个步骤
    public boolean isEnd()
    {
        return _position==_labels.length-1? true:false;
    }
    // 处于第一个步骤

    public boolean isStart()
    {
        return _position==0? true:false;
    }
}
