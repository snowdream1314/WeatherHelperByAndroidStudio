package com.snowdream1314.weatherhelper.bean;

/**
 * Created by xxq on 2016/8/18.
 */
public class UsercenterItem {

    private int id;
    private String title;
    private String icon;
    private String note;
    private String arrow;

//    public UsercenterItem(String title, int imageId, String note) {
//        this.title = title;
//        this.imageId = imageId;
//        this.note = note;
//    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public String getArrow() { return arrow; }

    public void setArrow(String arrow) { this.arrow = arrow; }
}
