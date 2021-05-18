package com.example.parkingintelligent.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.StaticMessage;

public class BillInformation extends Activity {
    protected TextView billState;
    protected TextView slotId;
    protected TextView startTime;
    protected TextView endTime;
    protected TextView cost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_information);
        billState = findViewById(R.id.bill_state);
        slotId = findViewById(R.id.slot_id);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        cost = findViewById(R.id.cost);

        // 填入数据
        if(StaticMessage.billModel.state==1)
            billState.setText("订单进行中");
        if(StaticMessage.billModel.state==2)
            billState.setText("订单待支付");
        if(StaticMessage.billModel.state==3)
            billState.setText("订单已完成");
        slotId.setText("停车场：" + StaticMessage.billModel.slotId);
//        String start = StaticMessage.billModel.startTime.split(".")[0];
//        String end = StaticMessage.billModel.endTime.split(".")[0];
        startTime.setText(StaticMessage.billModel.startTime);
        endTime.setText(StaticMessage.billModel.endTime);
        cost.setText(String.valueOf(StaticMessage.billModel.cost));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击外围，退出窗口
        this.finish();
        return true;
    }

}
