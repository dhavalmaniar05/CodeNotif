package com.example.cchelper;

import org.json.JSONObject;

public class CodeChefModel {
    JSONObject data;

    public CodeChefModel(JSONObject data) {
        this.data = data;
    }

    public JSONObject getData() {
        return data;
    }
}
