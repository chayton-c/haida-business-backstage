package com.yingda.lkj.beans.enums.planStype;

public enum PlanStyle {
    RED(0, "/upload/1"),
    BLUE(1, "/upload/2");


    private int styleLevel;
    private String imageUrl;

    PlanStyle(int styleLevel, String imageUrl) {
        this.styleLevel = styleLevel;
        this.imageUrl = imageUrl;
    }


}
