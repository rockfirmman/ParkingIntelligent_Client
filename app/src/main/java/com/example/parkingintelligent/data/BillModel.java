package com.example.parkingintelligent.data;

import java.util.Date;

public class BillModel {
    public BillModel(int id, int carId, int slotId, int fieldId, int payerId, int ownerId, String startTime, String endTime, float cost, int state, int score, String comments) {
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
    public BillModel(){
    }

    public int id;
    public int carId;
    public int slotId;
    public int fieldId;
    public int payerId;
    public int ownerId;
    public String startTime;
    public String endTime;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
