package com.example.parkingintelligent.data;

import java.util.Date;

public class BillModel {
    public BillModel(int id, int carId, int slotId, int fieldId, int payerId, int ownerId, Date startTime, Date endTime, float cost, int state, int score, String comments) {
        this.id = id;
        this.carId = carId;
        this.slotId = slotId;
        this.fieldId = fieldId;
        this.payerId = payerId;
        this.ownerId = ownerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.state = state;
        this.score = score;
        this.comments = comments;
    }

    public int id;
    public int carId;
    public int slotId;
    public int fieldId;
    public int payerId;
    public int ownerId;
    public Date startTime;
    public Date endTime;
    public float cost;
    public int state;
    public int score;
    public String comments;

    @Override
    public String toString() {
        return "BillModel{" +
                "id=" + id +
                ", carId=" + carId +
                ", slotId=" + slotId +
                ", fieldId=" + fieldId +
                ", payerId=" + payerId +
                ", ownerId=" + ownerId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", cost=" + cost +
                ", state=" + state +
                ", score=" + score +
                ", comments='" + comments + '\'' +
                '}';
    }
}
