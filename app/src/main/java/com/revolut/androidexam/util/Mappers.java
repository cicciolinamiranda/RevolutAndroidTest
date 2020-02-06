package com.revolut.androidexam.util;

import com.revolut.androidexam.model.dto.RateDTO;
import com.revolut.androidexam.model.dto.RemoteRateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mappers {

    public static List<RateDTO> mapRemoteToLocal(RemoteRateDTO remoteRateDTO){
        List<RateDTO> list = new ArrayList<>();
        for(Map.Entry<String, Double> current: remoteRateDTO.rates().entrySet())
            list.add(RateDTO.create(current.getKey(), current.getValue()));
        return list;
    }

    public static Double parseDouble(String s){
        try {
            return Double.parseDouble(s);
        }catch (NumberFormatException n){
            return 0.0;
        }
    }

}
