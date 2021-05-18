package com.example.parkingintelligent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.BillModel;

import java.util.List;
import java.util.Map;

public class BillAdapter extends BaseAdapter {
    // 数据集合
    List<Map<String,Object>> list;
    // 反射器
    LayoutInflater inflater;
    /**
     * 构造器
     * @param context 上下文
     */
    public BillAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }
    /**
     * 传入数据集合
     * @param list
     */
    public void setList(List<Map<String,Object>> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 反射行布局
        View view = inflater.inflate(R.layout.item,null);

        // 获取各个控件
        ImageView logo = view.findViewById(R.id.logo);
        TextView text1 = view.findViewById(R.id.text1);
        TextView text2 = view.findViewById(R.id.text2);
        TextView text3 = view.findViewById(R.id.text3);
        Button btn = view.findViewById(R.id.btn);

        // 给控件赋值
        logo.setImageResource( (Integer) list.get(position).get("logo"));
        text1.setText( (String) list.get(position).get("text1"));
        text2.setText( (String) list.get(position).get("text2"));
        text3.setText( (String) list.get(position).get("text3"));
        btn.setText( (String) list.get(position).get("btn_text"));
//        btn.setBackgroundColor(Color.parseColor("#a4b4cf"));
        btn.setBackgroundColor(Color.parseColor((String) list.get(position).get("btn_color")));
        // 已完成订单不可点击
        if(list.get(position).get("btn_text").equals("已完成"))
            btn.setEnabled(false);

        return view;
    }
}
