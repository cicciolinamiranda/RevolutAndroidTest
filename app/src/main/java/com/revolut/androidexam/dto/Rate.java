package com.revolut.androidexam.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
@AutoValue
public abstract class Rate {

    @SerializedName("name")
    public abstract String name();

    @SerializedName("value")
    public abstract Double value();

    public static Rate create(String name, Double value){
        return new AutoValue_Rate(name, value);
    }

    public static TypeAdapter<Rate> typeAdapter(Gson gson) {
        return new AutoValue_Rate.GsonTypeAdapter(gson);
    }

}
