package com.choicespropertysolutions.desta.model;

import com.choicespropertysolutions.desta.Singleton.ImageURLInstance;

public class MyImagesListItems {

    public Integer id;
    public String imagePath;
    public String imageCategory;
    public String caption;

    public MyImagesListItems() {
    }

    public MyImagesListItems(Integer id, String imagePath, String imageCategory, String caption) {
        this.id = id;
        this.imagePath = imagePath;
        this.imageCategory = imageCategory;
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = ImageURLInstance.getUrl() + "images/" + imagePath;
    }

    public String getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(String imageCategory) {
        this.imageCategory = imageCategory;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
