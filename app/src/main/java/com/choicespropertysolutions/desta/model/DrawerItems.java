package com.choicespropertysolutions.desta.model;

public class DrawerItems {

    private String title;
    private int image;

    public DrawerItems(){

    }

    public DrawerItems(String title, int imageUrl){

        this.title = title;
        this.image = imageUrl;
    }

    public String getTittle() {
        return title;
    }

    public void setTittle(String tittle) {
        this.title = tittle;
    }

    public int getIcon() {
        return image;
    }

    public void setIcons(int icon) {
        this.image = icon;
    }
}
