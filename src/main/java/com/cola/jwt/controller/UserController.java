package com.cola.jwt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cola.jwt.common.model.Result;
import com.cola.jwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/prod-api/user")
public class UserController {

    @Value("${login.username}")
    private String username;
    @Value("${login.password}")
    private String password;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody String userInfo){
        JSONObject jsonObject = JSON.parseObject(userInfo);
        String usernameFromPost = (String) jsonObject.get("username");
        String passwordFromPost = (String) jsonObject.get("password");

        if(username.equals(usernameFromPost) && password.equals(passwordFromPost)){
            JSONObject dataJson = new JSONObject();
            dataJson.put("token", JwtUtil.getToken(username));
            return Result.success("登录成功", dataJson);
        }

        return Result.error("账号或密码错误");
    }

    @RequestMapping(value = "/userinfo",method = RequestMethod.POST)
    public Result<JSONObject> userinfo(@RequestHeader("X-Token") String token){

        return Result.success(JwtUtil.getUserName(token));

    }
}
