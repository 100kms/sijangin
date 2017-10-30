package org.androidtown.sijang.Data;

/**
 * Created by minhyuk on 2017-10-22.
 */

public class MarketRank_Data {
    private String 시장이름, 교통수단, 내용, 소개, 사진경로, 주소;
    private double 경도, 위도;
    private long 리뷰수;

    public String get시장이름() {
        return 시장이름;
    }

    public void set시장이름(String 시장이름) {
        this.시장이름 = 시장이름;
    }

    public long get리뷰수() {
        return 리뷰수;
    }

    public void set리뷰수(long 리뷰수) {
        this.리뷰수 = 리뷰수;
    }

    public String get교통수단() {
        return 교통수단;
    }

    public void set교통수단(String 교통수단) {
        this.교통수단 = 교통수단;
    }

    public String get내용() {
        return 내용;
    }

    public void set내용(String 내용) {
        this.내용 = 내용;
    }

    public String get소개() {
        return 소개;
    }

    public void set소개(String 소개) {
        this.소개 = 소개;
    }

    public String get사진경로() {
        return 사진경로;
    }

    public void set사진경로(String 사진경로) {
        this.사진경로 = 사진경로;
    }

    public String get주소() {
        return 주소;
    }

    public void set주소(String 주소) {
        this.주소 = 주소;
    }

    public double get경도() {
        return 경도;
    }

    public void set경도(double 경도) {
        this.경도 = 경도;
    }

    public double get위도() {
        return 위도;
    }

    public void set위도(double 위도) {
        this.위도 = 위도;
    }
}