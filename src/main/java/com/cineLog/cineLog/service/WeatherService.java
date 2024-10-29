package com.cineLog.cineLog.service;


import com.cineLog.cineLog.api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private final  static String apiKey = "3daf716f58313567f31fa3393ce23337";

    private final  static String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;



    public WeatherResponse getWeather(String city){

//        String requestBody = "{\n" +
//            " \"username\":\"raj\",\n" +
//                " \"password\":\"raj\"\n" +
//              "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key","value");

        UserDetails user = User.builder().username("raj").password("raj").build();


        HttpEntity<UserDetails> httpEntity = new HttpEntity<>(user,httpHeaders);
        String finalAPI= API.replace("CITY",city).replace("API_KEY",apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);

        WeatherResponse body = response.getBody();

        return body;

    }

}
