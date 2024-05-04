package com.mikail.weather.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class Constants {
    
    public static String API_URL;

    public static String ACCESS_KEY_PARAM="?access_key=";

    public static String QUERY_KEY_PARAM="&query=";

    public static String API_KEY;

    @Value("${weather.stack.api-key}")
    public  void setAPI_KEY(String aPI_KEY) {
        Constants.API_KEY = aPI_KEY;
    }

    @Value("${weather.stack.api-url}")
    public  void setAPI_URL(String aPI_URL) {
        Constants.API_URL = aPI_URL;
    }
}
