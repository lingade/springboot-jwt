package com.cola.jwt.interceptor;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cola.jwt.common.model.Result;
import com.cola.jwt.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws IOException {
        if (httpServletRequest.getMethod().equals("OPTIONS")){
            return true;
        }
        String token = httpServletRequest.getHeader("X-Token");
        try {
            JwtUtil.verify(token);
        }catch (SignatureVerificationException e){
            //e.printStackTrace();
            System.out.println("无效签名");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getWriter().append(JSON.toJSONString(Result.error(Result.LOGIN_ERROR,"无效签名",null)));
            return false;
        }catch (TokenExpiredException e){
            //e.printStackTrace();
            System.out.println("token过期");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getWriter().append(JSON.toJSONString(Result.error(Result.LOGIN_ERROR,"token过期",null)));

            return false;
        }catch (AlgorithmMismatchException e){
            //e.printStackTrace();
            System.out.println("token算法不一致");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getWriter().append(JSON.toJSONString(Result.error(Result.LOGIN_ERROR,"token算法不一致",null)));

            return false;
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println("token无效");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.getWriter().append(JSON.toJSONString(Result.error(Result.LOGIN_ERROR,"token无效",null)));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
