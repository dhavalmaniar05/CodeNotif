package com.example.cchelper;

public class userDetails {
    private String name;
    private String codechef;
    private String codeforces;
    private String leetcode;

    public userDetails(){}

    public userDetails(String name, String codechef, String codeforces, String leetcode) {
        this.name = name;
        this.codechef = codechef;
        this.codeforces = codeforces;
        this.leetcode = leetcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodechef() {
        return codechef;
    }

    public void setCodechef(String codechef) {
        this.codechef = codechef;
    }

    public String getCodeforces() {
        return codeforces;
    }

    public void setCodeforces(String codeforces) {
        this.codeforces = codeforces;
    }

    public String getLeetcode() {
        return leetcode;
    }

    public void setLeetcode(String leetcode) {
        this.leetcode = leetcode;
    }
}
