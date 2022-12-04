package com.example.cchelper;

public class ContestPagesModel {
    private String hard;
    private String medium;
    private String easy;
    private String total;
    private String acceptance;
    private String total_easy;
   private String total_medium;
   private String total_hard;
    public ContestPagesModel(String hard, String medium, String easy, String total, String acceptance,String total_easy,String  total_medium,String total_hard) {
        this.hard = hard;
        this.medium = medium;
        this.easy = easy;
        this.total = total;
        this.acceptance = acceptance;
        this.total_easy=total_easy;
        this.total_medium=total_medium;
        this.total_hard=total_hard;
    }

    public String getTotal_medium() {
        return total_medium;
    }

    public void setTotal_medium(String total_medium) {
        this.total_medium = total_medium;
    }

    public String getTotal_hard() {
        return total_hard;
    }

    public void setTotal_hard(String total_hard) {
        this.total_hard = total_hard;
    }

    public String getTotal_easy() {
        return total_easy;
    }

    public void setTotal_easy(String total_easy) {
        this.total_easy = total_easy;
    }

    public String getHard() {
        return hard;
    }

    public void setHard(String hard) {
        this.hard = hard;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getEasy() {
        return easy;
    }

    public void setEasy(String easy) {
        this.easy = easy;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    @Override
    public String toString() {
        return "ContestPagesModel{" +
                "hard='" + hard + '\'' +
                ", medium='" + medium + '\'' +
                ", easy='" + easy + '\'' +
                ", total='" + total + '\'' +
                ", acceptance='" + acceptance + '\'' +
                '}';
    }
}
