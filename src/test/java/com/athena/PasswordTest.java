package com.athena;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

/**
 * @author Mr.sun
 * @date 2021/12/14 18:42
 * @description
 */
public class PasswordTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       /* String passworddd = passwordEncoder.encode("admin");
        System.err.println(passworddd);*/
        boolean res = passwordEncoder.matches("admin", "$2a$10$1TpP8nm7.Jz7RxNl7W99t.JaAtsnX9PfCTPL.TTYaRrDmwV6/LTNW");
        System.err.println(res);
    }

}
