package com.shivam.instagram.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody 
{
    
    private boolean success ;
    private Integer status;
    private Object data;
    private String message ;
    private String source ;

}
