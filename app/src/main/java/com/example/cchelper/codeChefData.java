package com.example.cchelper;

public class codeChefData {
    String username;
    Integer rating;
    Integer stars;
    Integer globalRank;
    Integer countryRank;
    Integer problemsSolved;
    Integer contestsParticipated;

    public codeChefData(String username, Integer rating, Integer stars, Integer globalRank, Integer countryRank, Integer problemsSolved, Integer contestsParticipated) {
        this.username = username;
        this.rating = rating;
        this.stars = stars;
        this.globalRank = globalRank;
        this.countryRank = countryRank;
        this.problemsSolved = problemsSolved;
        this.contestsParticipated = contestsParticipated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getGlobalRank() {
        return globalRank;
    }

    public void setGlobalRank(Integer globalRank) {
        this.globalRank = globalRank;
    }

    public Integer getCountryRank() {
        return countryRank;
    }

    public void setCountryRank(Integer countryRank) {
        this.countryRank = countryRank;
    }

    public Integer getProblemsSolved() {
        return problemsSolved;
    }

    public void setProblemsSolved(Integer problemsSolved) {
        this.problemsSolved = problemsSolved;
    }

    public Integer getContestsParticipated() {
        return contestsParticipated;
    }

    public void setContestsParticipated(Integer contestsParticipated) {
        this.contestsParticipated = contestsParticipated;
    }
}
