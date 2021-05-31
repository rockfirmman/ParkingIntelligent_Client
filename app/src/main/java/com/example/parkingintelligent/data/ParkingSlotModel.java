package com.example.parkingintelligent.data;

public class ParkingSlotModel {
    private int id;
    private boolean occupied;
    private boolean banned;
    private float length;
    private float width;
    private float height;
    private int hardwareId;
    private float price;
    private String name;
    private float longitude;
    private float latitude;
    private String pictureUrl;
    private int fieldId;
    private int ownerId;

    public ParkingSlotModel(){}

    public ParkingSlotModel(int id, boolean occupied, boolean banned, float length, float width, float height, int hardwareId, float price, String name, float longitude, float latitude, String pictureUrl, int fieldId, int ownerId) {
        this.id = id;
        this.occupied = occupied;
        this.banned = banned;
        this.length = length;
        this.width = width;
        this.height = height;
        this.hardwareId = hardwareId;
        this.price = price;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pictureUrl = pictureUrl;
        this.fieldId = fieldId;
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(int hardwareId) {
        this.hardwareId = hardwareId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "ParkingSlotModel{" +
                "id=" + id +
                ", occupied=" + occupied +
                ", banned=" + banned +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", hardwareId=" + hardwareId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", fieldId=" + fieldId +
                ", ownerId=" + ownerId +
                '}';
    }
}
