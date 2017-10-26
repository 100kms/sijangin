package org.androidtown.sijang.ReviewView;

/**
 * Created by 안탄 on 2017-10-15.
 */

public class Review {
    private String content;
    private String date;
    private int img_count=0;
    private String marketname;
    private String name;
    private double star;
    private String title;
    private String user_id;
    public Review(){

    }

    public Review(String content, String date, int img_count, String marketname, String name, double star, String title, String user_id) {
        this.content = content;
        this.date = date;
        this.img_count = img_count;
        this.marketname = marketname;
        this.name = name;
        this.star = star;
        this.title = title;
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImg_count() {
        return img_count;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }

    public String getMarketname() {
        return marketname;
    }

    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}