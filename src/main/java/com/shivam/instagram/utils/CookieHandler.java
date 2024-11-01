package com.shivam.instagram.utils;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CookieHandler 
{

    public Optional<Cookie> getDesiredCookie(HttpServletRequest httpServletRequest , String cookieName)
    {
        Cookie[] cookie = httpServletRequest.getCookies();

        if(cookie != null)
        {
            Optional<Cookie> auth_token_cookie =  Arrays.stream(cookie).filter(each -> each.getName().equals(cookieName)).findAny();
            return auth_token_cookie;
        }

        return Optional.empty();
    }

    public Cookie createCookie(HttpServletResponse httpServletResponse, String name , String value, String domain, String path, Integer durationInMilliSeconds,boolean httpOnly, boolean secure, String sameSite, String priority)
    {
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain(domain);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath(path);
        cookie.setMaxAge(durationInMilliSeconds);
        cookie.setSecure(secure);
        cookie.setAttribute("Priority", priority);
        cookie.setAttribute("SameSite", sameSite);

        return cookie;

    }


    public void setDesiredCookie(HttpServletResponse httpServletResponse, String name , String value, String domain, String path, Integer durationInMilliSeconds,boolean httpOnly, boolean secure, String sameSite, String priority)
    {
        Cookie cookie = createCookie(httpServletResponse, name, value, domain, path, durationInMilliSeconds, httpOnly, secure, sameSite, priority);


        httpServletResponse.addCookie(cookie);
    }


    public void setDesiredCookie(HttpServletResponse httpServletResponse, String name , String value, String domain,String path , Integer durationInMilliSeconds, String sameSite)
    {
       
        setDesiredCookie(httpServletResponse, name, value, domain,path, durationInMilliSeconds, true, true, sameSite,"");

    }
   

   
    public void setDesiredCookie(HttpServletResponse httpServletResponse, String name , String value,Integer durationInMilliSeconds)
    {
       
        setDesiredCookie(httpServletResponse, name, value, "shivamthakur.com.np","/", durationInMilliSeconds, true, true, "None","");

    }
    
}
