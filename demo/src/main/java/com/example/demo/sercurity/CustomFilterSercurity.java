package com.example.demo.sercurity;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class CustomFilterSercurity {
    @Autowired
    CustomUserDetaiService customUserDetaiService;
    @Autowired
    CustomJwtFilter customJwtFilter;
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity)throws Exception  {
//        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(customUserDetaiService);
//        return authenticationManagerBuilder.build();
//    }


    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(csrf -> csrf.disable())
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/login/**","/user/**","/orders/**","/payment/**","/products/**","/revenues/**","/shipping/**","/ordersmanagement/**","/Inventory/**","/InventoryTransaction/**").permitAll()
                         .requestMatchers("/admin/**").hasRole("ADMIN")
                         .anyRequest().authenticated()
                 ) ;
         http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
