package com.revolut.androidexam.model.dto;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
@AutoValue
public abstract class RateDTO {

    @SerializedName("name")
    public abstract String name();

    @SerializedName("value")
    public abstract Double value();

    public static RateDTO create(String name, Double value){
        return new AutoValue_RateDTO(name, value);
    }

    public static TypeAdapter<RateDTO> typeAdapter(Gson gson) {
        return new AutoValue_RateDTO.GsonTypeAdapter(gson);
    }

}
