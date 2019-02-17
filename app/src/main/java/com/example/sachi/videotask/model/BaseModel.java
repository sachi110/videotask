package com.example.sachi.videotask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class BaseModel implements Serializable {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    @Expose
    @SerializedName("id")
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
