package com.konex.servapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konex.servapp.auth.CurrentUser;
import com.konex.servapp.protocol.AuthResultResponse;
import com.konex.servapp.protocol.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kneimad on 28.09.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();// вимкнути csrf захист

        http.authorizeRequests()
                .antMatchers("/", "/login", "/registration", "/js/**", "/css/**").permitAll() // дозволити анонімним користувачам заходити на вказані ресурси
                .antMatchers("/admin","/admin/*").access("hasRole('ADMIN')") //сторінка адмін потребує ролі ROLE_ADMIN
                .anyRequest().authenticated() // всі інші запити потребують аутентифікації
                .and()
                .formLogin()
                .loginPage("/login")  // URL логіну
                .usernameParameter("username") // назва параметру з логіном на формі
                .failureUrl("/login?error") //
                .permitAll() // дозволити всім заходити на форму логіну
                .and()
                .logout()
                .permitAll();

        http.exceptionHandling()
                .accessDeniedPage("/access?error");

//        http.formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/hello")
//                .usernameParameter("username")
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .permitAll()
//                .and()
//                .httpBasic();
//
//        http.logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler(logoutHandler)
//                .deleteCookies("JSESSIONID")
//                .permitAll();
//
//        http.authorizeRequests()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/registration").permitAll()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

//        http.exceptionHandling()
//                .accessDeniedPage("/access?error");

//        http
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                .permitAll();
//
//        http
//                .logout()
//                .permitAll();
//
//        http
//                .exceptionHandling()
//                .accessDeniedPage("/access?error");
//
//        http
//                .authorizeRequests();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    /**
     * Returns user identifier in JSON format on authentication success.
     */
    private final AuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler() {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication auth) throws IOException, ServletException {
            System.out.println("***************** AuthenticationSuccessHandler");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON.toString());

            PrintWriter out = response.getWriter();
            System.out.println("***************** AuthenticationSuccessHandler-getPrincipal : " + auth.getPrincipal());
            CurrentUser user = (CurrentUser) auth.getPrincipal();
            System.out.println("***************** AuthenticationSuccessHandler-getPrincipal-CAST: " + user );
            mapper.writeValue(out, new AuthResultResponse(user.getId()));
            out.flush();
        }
    };

    /**
     * Returns authentication error in JSON format.
     */
    private final AuthenticationFailureHandler failureHandler = new AuthenticationFailureHandler() {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            System.out.println("***************** onAuthenticationFailure");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON.toString());

            PrintWriter out = response.getWriter();

            mapper.writeValue(out, new ErrorResponse("/login", "Authentication failed"));
            out.flush();
        }
    };

    /**
     * Changes default redirect logout handler to empty one, 'cause no redirect is necessary.
     */
    private final LogoutSuccessHandler logoutHandler = new SimpleUrlLogoutSuccessHandler() {
        @Override
        public void onLogoutSuccess(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    };

}
