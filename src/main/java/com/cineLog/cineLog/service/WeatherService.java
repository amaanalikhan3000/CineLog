package com.cineLog.cineLog.service;


import com.cineLog.cineLog.api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private final  static String apiKey = "3daf716f58313567f31fa3393ce23337";

    private final  static String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalAPI= API.replace("CITY",city).replace("API_KEY",apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        WeatherResponse body = response.getBody();

        return body;

    }

}
