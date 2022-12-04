package com.example.cchelper;

public class CodeForcesModel {
    private String firstname;
    private String lastname;
    private String rating;
    private String maxrating;
    private String rank;
    private String maxrank;
    private String organsiation;

    public CodeForcesModel(String firstname, String lastname, String rating, String maxrating, String rank, String maxrank, String organsiation) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.rating = rating;
        this.maxrating = maxrating;
        this.rank = rank;
        this.maxrank = maxrank;
        this.organsiation = organsiation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMaxrating() {
        return maxrating;
    }

    public void setMaxrating(String maxrating) {
        this.maxrating = maxrating;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMaxrank() {
        return maxrank;
    }

    public void setMaxrank(String maxrank) {
        this.maxrank = maxrank;
    }

    public String getOrgansiation() {
        return organsiation;
    }

    public void setOrgansiation(String organsiation) {
        this.organsiation = organsiation;
    }

    @Override
    public String toString() {
        return "CodeForcesModel{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", rating='" + rating + '\'' +
                ", maxrating='" + maxrating + '\'' +
                ", rank='" + rank + '\'' +
                ", maxrank='" + maxrank + '\'' +
                ", organsiation='" + organsiation + '\'' +
                '}';
    }
}
