package com.shivam.instagram.utils;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.instagram.dto.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil 
{
    

    public static <T> void sendJsonResponse(HttpServletResponse response , boolean success ,Integer status, Object data ,String message, String source ) throws IOException
    {

    
        response.setContentType("application/json");
        response.setStatus(200);
        
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseBody responseJson = new ResponseBody(success, status, data, message, source);

        String jsonString = objectMapper.writeValueAsString(responseJson);

        response.getWriter().write(jsonString);

    }    
}
