package com.wang.config;

import com.wang.filter.KaptchaLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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


//    @Override
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(User.withUsername("wanglei").password("{noop}123456").roles("admin").build());
//        return userDetailsManager;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//    @Bean
//    public LoginFilter loginFilter() throws Exception {
//        LoginFilter loginFilter = new LoginFilter();
//        loginFilter.setFilterProcessesUrl("/doLogin");
//        loginFilter.setUsernameParameter("username");
//        loginFilter.setPasswordParameter("password");
//        loginFilter.setAuthenticationManager(authenticationManagerBean());
//        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
//        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
//        return loginFilter;
//    }

    @Bean
    public KaptchaLoginFilter kaptchaLoginFilter() throws Exception {
        KaptchaLoginFilter kaptchaLoginFilter = new KaptchaLoginFilter();
        kaptchaLoginFilter.setFilterProcessesUrl("/doLogin");
        kaptchaLoginFilter.setUsernameParameter("username");
        kaptchaLoginFilter.setPasswordParameter("password");
        kaptchaLoginFilter.setKaptchaParameter("kaptcha");
        kaptchaLoginFilter.setAuthenticationManager(authenticationManagerBean());
        kaptchaLoginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        kaptchaLoginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        return kaptchaLoginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/verifyCode").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().print("尚未认证");
                })
                .and()
                .logout()
                .logoutSuccessHandler(new MyAuthenticationLogOutHandler())
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET"),
                        new AntPathRequestMatcher("/logout", "DELETE")
                ))
                .and()
                .csrf().disable();


//        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(kaptchaLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
