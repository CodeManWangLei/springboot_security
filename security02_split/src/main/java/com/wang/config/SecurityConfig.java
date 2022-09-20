package com.wang.config;

import com.wang.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public MyUserDetailService userDetailService;

    @Autowired
    public SecurityConfig(MyUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/doLogin");
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        return loginFilter;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request,response,exception) ->{
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().print("尚未认证");
                })
                .and()
                .logout()
                .logoutSuccessHandler(new MyAuthenticationLogOutHandler())
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout","GET"),
                        new AntPathRequestMatcher("/logout","DELETE")
                ))
                .and()
                .csrf().disable();

        
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
