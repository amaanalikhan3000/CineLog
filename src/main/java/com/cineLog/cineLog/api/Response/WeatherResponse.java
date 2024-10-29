package com.cineLog.cineLog.api.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class WeatherResponse {

    private Current current;


    @Getter
    @Setter
    public class Current {

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;


        @JsonProperty("feelslike")
        private int feelsLike;


        private int temperature;


    }


}
