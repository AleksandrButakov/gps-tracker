package ru.anbn.gpstracker;

import java.io.Serializable;

class User implements Serializable {
    private String trackerModel;
    private String password;
    private String numberTrackerSimCard;
    private String numberOwnerSimCard;

    public String getTrackerModel() {
        return trackerModel;
    }

    public void setTrackerModel(String trackerModel) {
        this.trackerModel = trackerModel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberTrackerSimCard() {
        return numberTrackerSimCard;
    }

    public void setNumberTrackerSimCard(String numberTrackerSimCard) {
        this.numberTrackerSimCard = numberTrackerSimCard;
    }

    public String getNumberOwnerSimCard() {
        return numberOwnerSimCard;
    }

    public void setNumberOwnerSimCard(String numberOwnerSimCard) {
        this.numberOwnerSimCard = numberOwnerSimCard;
    }

}
