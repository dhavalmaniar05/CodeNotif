package com.example.cchelper;

public class ListItems {
    private String desc;
    private String heading;
    private boolean isexpanded;


    public ListItems(String desc, String heading) {
        this.desc = desc;
        this.heading = heading;
        isexpanded=false;

    }

    public boolean isIsexpanded() {
        return isexpanded;
    }

    public void setIsexpanded(boolean isexpanded) {
        this.isexpanded = isexpanded;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "ListItems{" +
                "desc='" + desc + '\'' +
                ", heading='" + heading + '\'' +
                '}';
    }
}
