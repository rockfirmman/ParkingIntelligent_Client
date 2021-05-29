package com.example.parkingintelligent.data;

public class ParkingFieldModel {
    private int id;
    private boolean banned;
    private String name;
    private float longitude;
    private float latitude;
    private String description;
    private String pictureUrl;

    public ParkingFieldModel(int id, boolean banned, String name, float longitude, float latitude, String description, String pictureUrl) {
        this.id = id;
        this.banned = banned;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.pictureUrl = pictureUrl;
    }
    public ParkingFieldModel(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
