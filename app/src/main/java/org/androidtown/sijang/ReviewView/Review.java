package org.androidtown.sijang.ReviewView;

/**
 * Created by 안탄 on 2017-10-15.
 */

public class Review {
    private String content;
    private String date_record;
    private String image;
    private int image_count;
    private String market_text;
    private String replace_text;
    private float star;
    private String user_id;
    public Review(){

    }
    public Review(String content, String date_record, String image, int image_count, String market_text, String replace_text, float star, String user_id) {
        this.content = content;
        this.date_record = date_record;
        this.image = image;
        this.image_count = image_count;
        this.market_text = market_text;
        this.replace_text = replace_text;
        this.star = star;
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_record() {
        return date_record;
    }

    public void setDate_record(String date_record) {
        this.date_record = date_record;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImage_count() {
        return image_count;
    }

    public void setImage_count(int image_count) {
        this.image_count = image_count;
    }

    public String getMarket_text() {
        return market_text;
    }

    public void setMarket_text(String market_text) {
        this.market_text = market_text;
    }

    public String getReplace_text() {
        return replace_text;
    }

    public void setReplace_text(String replace_text) {
        this.replace_text = replace_text;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}