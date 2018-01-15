package com.incture.mobility.imagecachingpoc;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by satiswardash on 12/01/18.
 */

public class CardItem extends RealmObject {

    @PrimaryKey
    private String id;
    private String title;
    private String description;
    private RealmList<String> imageUris;

    public CardItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<String> getImageUris() {
        return imageUris;
    }

    public void setImageUris(RealmList<String> imageUris) {
        this.imageUris = imageUris;
    }
}
