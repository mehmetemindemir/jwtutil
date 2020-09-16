/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwtutil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author B2L5TKM
 */
public class JwtTest {
    public static void main(String[] args){
        JWTUtil jwtUtil = JWTUtil.create().setEnvironment("prod").build();
        String token = "";
        Map<String,String> claims = new HashMap<String,String>();
        claims.put("username","b2l5tkm");
        try {
            token = jwtUtil.generateTokenWithJWT(claims);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JwtTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoie1wiaWRcIjpcIjJcIixcIm9uZXJpVGlwaVwiOlwiSVlJTEVTVElSTUVcIixcImVrcmFuXCI6XCJ7XCJpZFwiOjksXCJ0aXBcIjpcIkRJR0VSXCIsXCJrb2RcIjpcIlBSVFVZRzAwOVwiLFwiYWNpa2xhbWFcIjpcIkFUTSDFnnViZSBLYXLFn8SxbGHFn3TEsXJtYSBVeWd1bGFtYXPEsVwifVwiLFwia2F0ZWdvcmlcIjpcIkVLUkFOX09ORVJJU0lcIixcImFzYW1hXCI6XCJ7XCJpZFwiOjEsXCJhY2lrbGFtYVwiOlwiVGFzbGFrXCIsXCJzaXJhXCI6MH1cIixcIm9uZXJpRHVydW1cIjpcIntcImlkXCI6MSxcImFjaWtsYW1hXCI6XCJBZG1pbmRlIEJla2xpeW9yXCJ9XCIsXCJpc2xlbVlldGtpbGVyaVwiOlwiW0dPUlVOVFVMRU1FLCBHVU5DRUxMRU1FXVwifSIsImlzcyI6Imt1cnVtc2FscG9ydGFseW9uZXRpbWk5NzBAaGFsa2JhbmsuY29tLnRyIiwiZXhwIjoxNTA2NjAyNTYwLCJlbnYiOiJkZXYiLCJpYXQiOjE1MDQ4NzQ1NjB9._XCrAxISbOy_QbhA1p_WfC51G591QvGy6l_iqMjaQ0g";
        System.out.println("token..:" + token);
        System.out.println("----------------------------------------------");
        System.out.println("valid..:" +jwtUtil.verifyToken(token));
        System.out.println("username..:" + jwtUtil.getDataFromKey(token,"username"));
    }
}
