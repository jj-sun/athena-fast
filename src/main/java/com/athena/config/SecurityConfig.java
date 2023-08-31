package com.athena.config;

import com.alibaba.fastjson.JSON;
import com.athena.common.constant.RedisConstant;
import com.athena.common.constant.SecurityConstant;
import com.athena.common.utils.JwtUtils;
import com.athena.common.utils.RedisUtils;
import com.athena.common.utils.Result;
import com.athena.filter.JwtAuthenticationTokenFilter;
import com.athena.modules.sys.form.LoginUser;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Mr.sun
 * @date 2021/12/14 11:29
 * @description
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private RedisUtils redisUtils;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider()).formLogin(formLogin -> {
                    formLogin.loginProcessingUrl("/login").successHandler((request, response, authentication) -> {
                        //登录成功后，配置token
                        //生成token，然后存到redis
                        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                        String token = JwtUtils.generateToken(loginUser.getUsername(), loginUser.getPassword());
                        //默认两个个小时
                        redisUtils.set(RedisConstant.PREFIX_USER_TOKEN + token, token, JwtUtils.EXPIRE/1000);
                        Map<String, Object> result = Maps.newHashMap();
                        result.put("token", token);
                        result.put("userInfo", loginUser.getUser());


                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.OK.value());
                        PrintWriter out = response.getWriter();
                        out.write(JSON.toJSONString(Result.ok( "login success", result)));
                        out.flush();
                        out.close();
                    }).failureHandler((request, response, exception) -> {
                        log.error(exception.getMessage());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        PrintWriter out = response.getWriter();
                        out.write(JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED.value(), "login error")));
                        out.flush();
                        out.close();
                    });
                })
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.OPTIONS).permitAll()
                            .requestMatchers("/favicon.ico").permitAll()
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/druid/**").permitAll()
                            .requestMatchers("/actuator/**").permitAll()
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                        //未经验证时
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        PrintWriter out = response.getWriter();
                        out.write(JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED.value(), "not login")));
                        out.flush();
                        out.close();
                    }).accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        PrintWriter out = response.getWriter();
                        out.write(JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED.value(), "权限不足")));
                        out.flush();
                        out.close();
                    });
                })
                .logout(logout -> {
                    logout.logoutSuccessHandler((request, response, authentication) -> {
                        String accessToken = request.getHeader(SecurityConstant.X_ACCESS_TOKEN);
                        if(StringUtils.isNotBlank(accessToken)) {
                            redisUtils.delete(RedisConstant.PREFIX_USER_TOKEN + accessToken);
                        }
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.OK.value());
                        PrintWriter out = response.getWriter();
                        out.write(JSON.toJSONString(Result.error(HttpStatus.OK.value(), "logout success")));
                        out.flush();
                        out.close();
                    }).permitAll();
                })
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
     public DaoAuthenticationProvider daoAuthenticationProvider() {
         DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(userDetailsService);
         authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
         return authenticationProvider;
     }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        //Encryptors KeyGenerators
        return new BCryptPasswordEncoder();
    }
}
